// ##########################################################################
// # File Name:	BlockingQueueDispatcher.java
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

import strata1.common.utility.IMultiMap;
import strata1.common.utility.MultiMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public
class BlockingQueueDispatcher<T>
    extends    AbstractPriorityDispatcher<T>
    implements IPriorityDispatcher<T>
{
    private ExecutorService                                 itsPool;
    private IMultiMap<ISelector<T>,BlockingQueue<Entry<T>>> itsQueues;
    private AtomicBoolean                                   itsRunningFlag;
    private AtomicLong                                      itsSequence;
    
    /************************************************************************
     * Creates a new {@code BlockingQueueDispatcher}. 
     *
     */
    protected 
    BlockingQueueDispatcher()
    {
        itsPool   = null;
        itsQueues = new MultiMap<ISelector<T>,BlockingQueue<Entry<T>>>();
        itsRunningFlag = new AtomicBoolean(false);
        itsSequence = new AtomicLong(0L);
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    route(int priority,T payload)
    {
        long sequence = 0L;
        
        if ( priority < 0 )
            throw 
                new IllegalArgumentException("priority cannot be negative.");
        
        if ( !isDispatching() )
            throw new IllegalStateException("Not dispatching.");
        
        sequence = itsSequence.getAndIncrement();
        
        for (ISelector<T> selector:itsQueues.getKeys())
        {
            if ( selector.match( payload ) )
            {
                BlockingQueue<Entry<T>> queue = 
                    getQueue( selector,sequence );
                
                try
                {
                    queue.put( new Entry<T>(priority,payload ) );
                }
                catch(InterruptedException e)
                {
                }
            }
        } 
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    broadcast(int priority,T payload)
    {
        if ( priority < 0 )
            throw 
                new IllegalArgumentException("priority cannot be negative.");

        if ( !isDispatching() )
            throw new IllegalStateException("Not dispatching.");
        
        for (ISelector<T> selector:itsQueues.getKeys())
        {
            if ( selector.match( payload ) )
            {
                for (BlockingQueue<Entry<T>> queue:itsQueues.get( selector ) )
                {
                    try
                    {
                        queue.put( new Entry<T>(priority,payload ) );
                    }
                    catch(InterruptedException e)
                    {
                    }
                }
            }
        }        
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startDispatching()
    {
        if ( !isDispatching() )
        {
            getRunningFlag().compareAndSet( false,true );
            setExecutor( Executors.newCachedThreadPool() );
           
            for (ISelector<T> selector:getConsumers().getKeys())                
            {
                for (IConsumer<T> consumer:getConsumers().get( selector ))
                {
                    BlockingQueue<Entry<T>> queue = 
                        new PriorityBlockingQueue<Entry<T>>();
                    
                    addToQueues( selector,queue );
                    getExecutor().execute( 
                        new BlockingQueueProcessor<T>(
                            queue,
                            consumer,
                            getRunningFlag()) );
                }
            }
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopDispatching()
    {
        if ( isDispatching() )
        {
            itsRunningFlag.compareAndSet( true,false );
            itsPool.shutdown();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isDispatching()
    {
        return
            (itsRunningFlag.get() == true) &&
            (itsPool != null) &&
            (!itsPool.isShutdown()) &&
            (!itsPool.isTerminated());     
    }

    /************************************************************************
     *  
     *
     * @param executor
     */
    protected void
    setExecutor(ExecutorService executor)
    {
        itsPool = executor;
    }
    
    /************************************************************************
     *  
     *
     * @param selector
     * @param queue
     */
    protected void
    addToQueues(ISelector<T> selector,BlockingQueue<Entry<T>> queue)
    {
        itsQueues.put( selector,queue );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected ExecutorService
    getExecutor()
    {
        return itsPool;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected AtomicBoolean
    getRunningFlag()
    {
        return itsRunningFlag;
    }
    
    /************************************************************************
     *  
     *
     * @param queues
     * @param sequence
     * @return
     */
    protected BlockingQueue<Entry<T>>
    getQueue(ISelector<T> selector,long sequence)
    {
        int cardinality = itsQueues.getCardinality( selector );
        
        return 
            itsQueues
                .get( selector,(int)sequence % cardinality );
    }
}

// ##########################################################################
