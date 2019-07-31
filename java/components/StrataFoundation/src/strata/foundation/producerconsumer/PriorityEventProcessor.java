// ##########################################################################
// # File Name:	PriorityEventProcessor.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.producerconsumer;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;
import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.Sequencer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.TimeoutHandler;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PriorityEventProcessor<T>
    implements EventProcessor
{
    private static final int RETRIES_YEILD = 50;
    
    private final DisruptorPriorityLevel<T>[] itsPriorities;
    private final Sequence[]                  itsSequences;
    private final EventHandler<T>             itsEventHandler; 
    private final ExceptionHandler            itsExceptionHandler;
    private final AtomicBoolean               itsRunningFlag;
    
    /************************************************************************
     * Creates a new {@code PriorityEventProcessor}. 
     *
     */
    @SuppressWarnings("unchecked")
    public 
    PriorityEventProcessor(
        final RingBuffer<T>[]  buffers,
        final EventHandler<T>  eventHandler,
        final ExceptionHandler exceptionHandler)
    {
        itsPriorities       = new DisruptorPriorityLevel[buffers.length];
        itsSequences        = new Sequence[buffers.length];
        itsEventHandler     = eventHandler;
        itsExceptionHandler = exceptionHandler;
        itsRunningFlag      = new AtomicBoolean(false);
        
        for (int i=0;i<buffers.length;i++)
        {
            itsPriorities[i] = new DisruptorPriorityLevel<T>(buffers[i]);
            itsSequences[i] = new Sequence(Sequencer.INITIAL_CURSOR_VALUE);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run()
    {
        final int numPriorities = itsPriorities.length;
        boolean[] processedSequence = new boolean[numPriorities];
        long[]    cachedAvailableSequence = new long[numPriorities];
        long[]    nextSequence = new long[numPriorities];
        
        if (!itsRunningFlag.compareAndSet(false, true))
            throw 
                new IllegalStateException(
                    "EventProcessor is already running");
        
        for (DisruptorPriorityLevel<T> priority : itsPriorities)
            priority
                .getBarrier()
                .clearAlert();
        
        notifyStart();
        
        for (int i=0;i<numPriorities;i++)
        {
            processedSequence[i] = true;
            cachedAvailableSequence[i] = Long.MIN_VALUE;
        }
        
        while (true)
        {
            try
            {
                for (int i = 0; i < numPriorities; i++)
                {
                    processEvents( 
                        processedSequence,
                        cachedAvailableSequence,
                        nextSequence,
                        i );
                }
            }
            catch (AlertException e)
            {
                if (!isRunning())
                    break;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (TimeoutException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                break;
            }
            finally 
            {
                notifyShutdown();
                itsRunningFlag.set(false);
            }
        }
        
            /*
        while (true)
        {
            try
            {
                for (int i = 0; i < numPriorities; i++)
                {
                    DisruptorPriorityLevel<T> priority  = itsPriorities[i];
                    long                      available = priority.getBarrier().waitFor(-1);
                    Sequence                  sequence  = priority.getSequence();
                    long                      previous  = sequence.get();

                    for (long n = previous + 1; n <= available; n++)
                    {
                        T event = 
                            priority
                                .getBuffer()
                                .get(n);
                       
                        itsEventHandler.onEvent( 
                            event,
                            n,
                            previous == available );
                    }
                    
                    sequence.set(available);
                }
    
                Thread.yield();
            }
            */
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Sequence 
    getSequence()
    {
        throw 
            new UnsupportedOperationException(
                "Call getSequence(int) instead!");    
    }

    /************************************************************************
     *  
     *
     * @param i
     * @return
     */
    public Sequence
    getSequence(int i)
    {
        return 
            itsPriorities[i].getSequence();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public DisruptorPriorityLevel<T>[]
    getPriorities()
    {
        return itsPriorities;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    halt()
    {
        itsRunningFlag.set(false);
        
        for (DisruptorPriorityLevel<T> priority : itsPriorities)
            priority
                .getBarrier()
                .alert();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRunning()
    {
        return itsRunningFlag.get();
    }
    
    /************************************************************************
     *  
     *
     * @param processedSequence
     * @param cachedAvailableSequence
     * @param nextSequence
     * @param i
     * @return
     * @throws AlertException
     * @throws InterruptedException
     * @throws TimeoutException
     * @throws Exception
     */
    private int 
    processEvents(
        boolean[] processedSequence,
        long[]    cachedAvailableSequence,
        long[]    nextSequence,
        int       i)
        throws 
            AlertException,
            InterruptedException,
            TimeoutException,Exception
    {
        T event;
        DisruptorPriorityLevel<T> priority  = itsPriorities[i];
        @SuppressWarnings("unused")
        long                      available = priority.getBarrier().waitFor(-1);
        Sequence                  workSequence  = priority.getSequence();
    
        // if previous sequence was processed - fetch the next sequence and set
        // that we have successfully processed the previous sequence
        // typically, this will be true
        // this prevents the sequence getting too far forward if an exception
        // is thrown from the WorkHandler
        if (processedSequence[i])
        {
            processedSequence[i] = false;
            do
            {
                nextSequence[i] = workSequence.get() + 1L;
                itsSequences[i].set(nextSequence[i] - 1L);
            }
            while (!workSequence.compareAndSet(nextSequence[i] - 1L, nextSequence[i]));
        }
    
        if (cachedAvailableSequence[i] >= nextSequence[i])
        {
            event = 
                priority
                    .getBuffer()
                    .get(nextSequence[i]);
           
            itsEventHandler.onEvent( 
                event,
                nextSequence[i],
                nextSequence[i] == cachedAvailableSequence[i] );
            processedSequence[i] = true;
            return 1;
        }
        else
        {
            cachedAvailableSequence[i] = priority.getBarrier().waitFor(nextSequence[i]);
            return 0;
        }
    }

    /************************************************************************
     *  
     *
     * @param counter
     * @return
     */
    @SuppressWarnings("unused")
    private int 
    waitWhenAllEmpty(int counter) 
    {
        for (DisruptorPriorityLevel<T> priority:itsPriorities) 
        {
            try 
            {
                priority.getBarrier().checkAlert();
            } 
            catch (AlertException ex) 
            {
                ex.printStackTrace();
            }
        }
        
        if (counter > RETRIES_YEILD) 
        {
            --counter;
        } 
        else if (counter > 0) 
        {
            --counter;
            Thread.yield();
        } 
        else 
        {
            LockSupport.parkNanos(1L);
        }
        
        return counter;
    }

    /************************************************************************
     *  
     *
     * @param availableSequence
     */
    @SuppressWarnings("unused")
    private void 
    notifyTimeout(final long sequence) 
    {
        try 
        {
            if ( itsEventHandler instanceof TimeoutHandler)
                ((TimeoutHandler)itsEventHandler).onTimeout( sequence );
        } 
        catch (final Throwable e) 
        {
            itsExceptionHandler.handleEventException( e,sequence,null );
        }
    }

    /************************************************************************
     *  
     *
     */
    private void 
    notifyStart() 
    {
        try 
        {
            if ( itsEventHandler instanceof LifecycleAware)
                ((LifecycleAware)itsEventHandler).onStart();
        } 
        catch (final Throwable e) 
        {
            itsExceptionHandler.handleOnStartException( e );
        }
    }

    /************************************************************************
     *  
     *
     */
    private void 
    notifyShutdown() 
    {
        try 
        {
            if ( itsEventHandler instanceof LifecycleAware)
                ((LifecycleAware)itsEventHandler).onShutdown();
        } 
        catch (final Throwable e) 
        {
            itsExceptionHandler.handleOnShutdownException( e );
        }
    }
}

// ##########################################################################
