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

package strata1.common.producerconsumer;

import com.lmax.disruptor.AlertException;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.TimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

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
    private final List<DisruptorPriorityLevel<T>> itsPriorities;
    private final List<DisruptorEventHandler<T>>  itsHandlers; 
    private final ExceptionHandler                itsHandler;
    private final AtomicBoolean                   itsRunningFlag;
    
    private static final int RETRY_SPIN  = 100;
    private static final int RETRY_YEILD = 50;
    
    /************************************************************************
     * Creates a new {@code PriorityEventProcessor}. 
     *
     */
    public 
    PriorityEventProcessor(
        final List<RingBuffer<Event<T>>>     buffers,
        final List<DisruptorEventHandler<T>> handlers,
        final ExceptionHandler               handler)
    {
        itsPriorities  = new ArrayList<DisruptorPriorityLevel<T>>();
        itsHandlers    = new ArrayList<DisruptorEventHandler<T>>(handlers);
        itsHandler     = handler;
        itsRunningFlag = new AtomicBoolean(false);
        
        for (RingBuffer<Event<T>> buffer:buffers)
            itsPriorities.add( new DisruptorPriorityLevel<T>(buffer) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run()
    {
        if (!itsRunningFlag.compareAndSet(false, true))
            throw new IllegalStateException("EventProcessor is already running");
        
        for (DisruptorPriorityLevel<T> priority : itsPriorities)
            priority
                .getBarrier()
                .clearAlert();
        
        notifyStart();
        
        try 
        {
            int counter = RETRY_SPIN;
            
            while ( itsRunningFlag.get() ) 
            {
                boolean allEmpty = true;
                
                for (DisruptorPriorityLevel<T> priority:itsPriorities) 
                    if ( process( priority ) > 0 )
                        allEmpty = false;
                                    
                if (allEmpty)
                    counter = waitWhenAllEmpty(counter);
                else 
                    counter = RETRY_SPIN;
            }
        } 
        finally 
        {
            notifyShutdown();
            itsRunningFlag.set(false);
        }
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
     * @param i
     * @return
     */
    public Sequence
    getSequence(int i)
    {
        return 
            itsPriorities
                .get( i )
                .getSequence();
    }
    
    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    private int 
    process(DisruptorPriorityLevel<T> priority)
    {
        Event<T> event        = null;
        long     beginSeq     = priority.getSequence().get();
        long     curSeq       = beginSeq + 1;
        long     availableSeq = beginSeq;
        int      batch        = 1;
        
        try 
        {
            availableSeq = 
                priority
                    .getBarrier()
                    .waitFor(curSeq);
            
            if ( availableSeq <= beginSeq )
                return 0;
            
            while ((curSeq <= availableSeq) && (curSeq - beginSeq <= batch)) 
            {
                event = 
                    priority
                        .getBuffer()
                        .get(curSeq);
                
                for (DisruptorEventHandler<T> handler:itsHandlers)
                {
                    try 
                    {
                        handler.onEvent(event, curSeq, curSeq == availableSeq);
                    } 
                    catch (final Throwable e) 
                    {
                        itsHandler.handleEventException( e,curSeq,event );
                    }
                }
               
                
                curSeq++;
            }
            
            curSeq--;
            
            priority
                .getSequence()
                .set(curSeq);
            
        } 
        catch (final TimeoutException e) 
        {
            notifyTimeout(priority.getSequence().get());
        } 
        catch (final AlertException e) 
        {
            if (!itsRunningFlag.get())
                return -1;
        } 
        catch (final Throwable e) 
        {
            itsHandler.handleEventException( e,curSeq,event );
            priority
                .getSequence()
                .set(curSeq);
        }
        return (int) (curSeq - beginSeq);
    }

    /************************************************************************
     *  
     *
     * @param counter
     * @return
     */
    private int 
    waitWhenAllEmpty(int counter)
    {
        for (DisruptorPriorityLevel<T> priority:itsPriorities) 
        {
            try 
            {
                priority
                    .getBarrier()
                    .checkAlert();
            } 
            catch (AlertException ex) 
            {
                ex.printStackTrace();
            }
        }
        
        if (counter > RETRY_YEILD) 
            --counter;
        else if (counter > 0) 
        {
            --counter;
            Thread.yield();
        } 
        else
            LockSupport.parkNanos(1L);
       
        return counter;
    }

    /************************************************************************
     *  
     *
     * @param availableSequence
     */
    private void 
    notifyTimeout(final long sequence) 
    {
        for (DisruptorEventHandler<T> handler:itsHandlers)
        {
            try 
            {
                handler.onTimeout( sequence );
            } 
            catch (final Throwable e) 
            {
                itsHandler.handleEventException( e,sequence,null );
            }
        }       
    }

    /************************************************************************
     *  
     *
     */
    private void 
    notifyStart() 
    {
        for (DisruptorEventHandler<T> handler:itsHandlers)
        {
            try 
            {
                handler.onStart();
            } 
            catch (final Throwable e) 
            {
                itsHandler.handleOnStartException( e );
            }
        }
    }

    /************************************************************************
     *  
     *
     */
    private void 
    notifyShutdown() 
    {
        for (DisruptorEventHandler<T> handler:itsHandlers)
        {
            try 
            {
                handler.onShutdown();
            } 
            catch (final Throwable e) 
            {
                itsHandler.handleOnShutdownException( e );
            }
        }
    }
}

// ##########################################################################
