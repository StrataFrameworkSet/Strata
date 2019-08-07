// ##########################################################################
// # File Name:	PriorityDisruptor.java
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

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.TimeoutException;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PriorityDisruptor<T>
{
    private final RingBuffer<T>[]                 itsBuffers;
    private final List<PriorityEventProcessor<T>> itsProcessors;
    private final AtomicBoolean                   itsStartedFlag;
    private final ExecutorService                 itsExecutor;
    private ExceptionHandler                      itsHandler;
    
    
    /************************************************************************
     * Creates a new {@code PriorityDisruptor}. 
     *
     */
    @SuppressWarnings("unchecked")
    public 
    PriorityDisruptor(
        final EventFactory<T> factory,
        final int             numPriorities,
        final int             bufferSize,
        final ProducerType    producerType)
    {
        itsBuffers     = new RingBuffer[numPriorities];
        itsProcessors  = new ArrayList<PriorityEventProcessor<T>>();
        itsStartedFlag = new AtomicBoolean(false);
        itsExecutor    = Executors.newCachedThreadPool();
        itsHandler     = null;
        
        for (int i=0;i<numPriorities;i++)
            itsBuffers[i] =  
                RingBuffer.create(
                    producerType, 
                    factory, 
                    bufferSize, 
                    new SkippingWaitStrategy());
    }

    /************************************************************************
     *  
     *
     * @param handler
     */
    public void 
    handleEventsWith(final EventHandler<T> handler) 
    {
        if (itsStartedFlag.get())
            throw 
                new IllegalStateException(
                    "Event handlers must be added before start.");
        
        itsProcessors.add( 
            new PriorityEventProcessor<T>(
                itsBuffers, 
                handler,
                itsHandler ) );
    }

    /************************************************************************
     *  
     *
     * @param array
     */
    public void 
    handleEventsWith(EventHandler<T>... handlers)
    {
        for (EventHandler<T> handler:handlers)
            handleEventsWith(handler);
    }

    /************************************************************************
     *  
     *
     * @param disruptorExceptionHandler
     */
    public void 
    handleExceptionsWith(ExceptionHandler handler)
    {
        itsHandler = handler;
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @param eventTranslator
     */
    public void 
    publishEvent(
        final int                priority, 
        final EventTranslator<T> translator) 
    {
        itsBuffers[priority].publishEvent(translator);
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @param translator
     * @param arg
     */
    public <A> void 
    publishEvent(
        final int                        priority, 
        final EventTranslatorOneArg<T,A> translator, 
        A                                arg) 
    {
        itsBuffers[priority].publishEvent(translator,arg);
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @param sequence
     */
    public void 
    publish(final int priority,final long sequence) 
    {
        itsBuffers[priority].publish( sequence );
        System.out.println( "debug 1");
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public PriorityDisruptor<T> 
    start() 
    {   
        if (!itsStartedFlag.compareAndSet(false, true)) 
            return this;
        
        if ( itsProcessors.isEmpty() )
            throw 
                new IllegalStateException(
                    "handleEventsWith() should be called before start.");
        
        for (PriorityEventProcessor<T> processor:itsProcessors)
            itsExecutor.execute( processor );
        
        return this;
    }

    /************************************************************************
     *  
     *
     */
    public void 
    halt() 
    {
        for (PriorityEventProcessor<T> processor:itsProcessors)
            processor.halt();
    }

    /************************************************************************
     *  
     *
     */
    public void 
    shutdown() 
    {
        try 
        {
            shutdown(-1, TimeUnit.MILLISECONDS);
        } 
        catch (TimeoutException e) 
        {
            itsHandler.handleOnShutdownException(e);
        }
    }

    /************************************************************************
     *  
     *
     * @param timeout
     * @param timeUnit
     * @throws TimeoutException
     */
    public void 
    shutdown(final long timeout, final TimeUnit timeUnit) 
        throws TimeoutException 
    {
        long timeOutAt = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        
        while (true) 
        {
            for (int i = 0; i < itsBuffers.length; i++) 
            {
                if (!hasBacklog(i)) 
                {
                    halt();
                    return;
                }
                
                if (timeout >= 0 && System.currentTimeMillis() > timeOutAt)
                    throw TimeoutException.INSTANCE;
                
                // Busy spin
            }
        }
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    public RingBuffer<T> 
    getRingBuffer(final int priority) 
    {
        return itsBuffers[priority];
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    public long 
    getCursor(final int priority) 
    {
        return getRingBuffer(priority).getCursor();
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    public long 
    getBufferSize(final int priority) 
    {
        return getRingBuffer(priority).getBufferSize();
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @param sequence
     * @return
     */
    public T 
    get(final int priority,final long sequence) 
    {
        return getRingBuffer(priority).get(sequence);
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    private boolean 
    hasBacklog(int priority) 
    {
        final long cursor = getRingBuffer(priority).getCursor();
        
        for (PriorityEventProcessor<T> processor:itsProcessors)
            for (DisruptorPriorityLevel<T> level : processor.getPriorities())
                if (cursor > level.getSequence().get())
                    return true;

        return false;
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     */
    public long 
    next(int priority) 
    {
        return getRingBuffer(priority).next();
    }

    /************************************************************************
     *  
     *
     * @param priority
     * @return
     * @throws InsufficientCapacityException
     */
    public long 
    tryNext(int priority) 
        throws InsufficientCapacityException 
    {
        return getRingBuffer(priority).tryNext();
    }
}

// ##########################################################################
