// ##########################################################################
// # File Name:	BlockingQueueBroadcaster.java
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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BlockingQueueBroadcaster<T>
    extends BlockingQueueDispatcher<T>
{

    /************************************************************************
     * Creates a new {@code BlockingQueueRouter}. 
     *
     */
    public 
    BlockingQueueBroadcaster()
    {
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
                    BlockingQueue<T> queue = new LinkedBlockingQueue<T>();
                    
                    addToQueues( selector,queue );
                    getExecutor().execute( 
                        new BlockingCollectionProcessor<T>(
                            queue,
                            consumer,
                            getRunningFlag()) );
                }
            }
        }
    }
}

// ##########################################################################
