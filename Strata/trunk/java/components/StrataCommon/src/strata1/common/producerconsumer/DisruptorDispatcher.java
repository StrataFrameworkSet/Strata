// ##########################################################################
// # File Name:	DisruptorDispatcher.java
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

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 * @param <T>
 */
public
class DisruptorDispatcher<T>
    extends    AbstractDispatcher<T>
    implements IDispatcher<T>
{
    private final PriorityDisruptor<Event<T>> itsDisruptor;
    private final AtomicBoolean               itsRoutingIndicator;

    /************************************************************************
     * Creates a new {@code DisruptorDispatcher}. 
     *
     */
    protected 
    DisruptorDispatcher(
        IEventFactory<T> factory,
        int              numPriorities,
        int              bufferSize)
    {
        itsDisruptor = 
            new PriorityDisruptor<Event<T>>(
                factory,
                numPriorities,
                bufferSize,
                ProducerType.MULTI);
        itsDisruptor.handleExceptionsWith( 
            new DisruptorExceptionHandler(
                new PrintStackTraceExceptionHandler()) );
        
        itsRoutingIndicator = new AtomicBoolean(false);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    route(int priority,T payload)
    {
        RingBuffer<Event<T>> buffer = itsDisruptor.getRingBuffer(priority);
        long                 sequence = buffer.next();
        Event<T>             event    = buffer.get( sequence );
        
        event.setKind( DispatchKind.ROUTE );
        event.setPayload( payload );        
        buffer.publish(sequence);
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    broadcast(int priority,T payload)
    {
        RingBuffer<Event<T>> buffer = itsDisruptor.getRingBuffer(priority);
        long                 sequence = buffer.next();
        Event<T>             event    = buffer.get( sequence );
        
        event.setKind( DispatchKind.BROADCAST );
        event.setPayload( payload );        
        buffer.publish(sequence);
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public void 
    startDispatching()
    {
        List<DisruptorEventHandler<T>> eventHandlers = 
            new ArrayList<DisruptorEventHandler<T>>();
        
        for (List<IConsumer<T>> consumers:getConsumers().getValues())
        {
            long index       = 0;
            long cardinality = consumers.size();
            
            for (IConsumer<T> consumer:consumers)
                eventHandlers.add( 
                    createHandler(
                        index++,
                        cardinality,
                        consumer ) );
        }
        
        itsDisruptor.handleEventsWith( 
            eventHandlers.toArray( 
                new DisruptorEventHandler[eventHandlers.size()] ) );
        
        itsDisruptor.start();
        itsRoutingIndicator.set( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopDispatching()
    {
        itsDisruptor.shutdown();
        itsRoutingIndicator.set( false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isDispatching()
    {
        return itsRoutingIndicator.get();
    }
    
    /************************************************************************
     *  
     *
     * @param index
     * @param cardinality
     * @param consumer
     * @return
     */
    protected DisruptorEventHandler<T>
    createHandler(long index,long cardinality,IConsumer<T> consumer)
    {
        return new DisruptorEventHandler<T>(index,cardinality,consumer);
    }
}
// ##########################################################################
