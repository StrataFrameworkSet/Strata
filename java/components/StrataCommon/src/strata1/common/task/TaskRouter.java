// ##########################################################################
// # File Name:	TaskRouter.java
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

package strata1.common.task;

import strata1.common.producerconsumer.BlockingCollectionClosedException;
import strata1.common.producerconsumer.BlockingCollectionCompletedException;
import strata1.common.utility.ISynchronizer;
import strata1.common.utility.ReadWriteLockSynchronizer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TaskRouter
    implements ITaskRouter
{
    private Set<ITaskConsumer> itsConsumers;
    private ISynchronizer      itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code TaskRouter}. 
     *
     */
    public 
    TaskRouter()
    {
        itsConsumers = new HashSet<ITaskConsumer>();
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    attachConsumer(ITaskConsumer consumer)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            if ( !hasConsumer( consumer ) )
                itsConsumers.add( consumer );
                
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    detachConsumer(ITaskConsumer consumer)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            if ( hasConsumer( consumer ) )
                itsConsumers.remove( consumer );
                
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     * @throws InterruptedException 
     * @throws BlockingCollectionCompletedException 
     * @throws BlockingCollectionClosedException 
     */
    @Override
    public void 
    routeElement(ITask task) throws BlockingCollectionClosedException, BlockingCollectionCompletedException, InterruptedException
    {
        try
        {
            ITaskConsumer consumer = null;
            
            itsSynchronizer.lockForReading();
            consumer = selectConsumer(task);
            consumer.putElement(task);
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(ITaskConsumer consumer)
    {
        try
        {
            itsSynchronizer.lockForReading();
            return itsConsumers.contains( consumer );
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     *  
     *
     * @param task
     * @return
     */
    private ITaskConsumer 
    selectConsumer(ITask task)
    {
        List<ITaskConsumer> matching = new ArrayList<ITaskConsumer>();
        
        matching = filterOnSelector(itsConsumers,task);
        matching = filterOnMin(matching,findMin(matching));
        return matching.get( generateRandomIndex(matching.size()) );
    }

    /************************************************************************
     *  
     *
     * @param consumers
     * @param task
     * @return
     */
    private List<ITaskConsumer> 
    filterOnSelector(Set<ITaskConsumer> consumers,ITask task)
    {
        List<ITaskConsumer> selected = new ArrayList<ITaskConsumer>();
        
        for (ITaskConsumer consumer:consumers)
            if ( consumer.getSelector().match( task ) )
                selected.add( consumer );
        
        return selected;
    }

    /************************************************************************
     *  
     *
     * @param matching
     * @param findMin
     * @return
     */
    private List<ITaskConsumer> 
    filterOnMin(List<ITaskConsumer> matching,int min)
    {
        List<ITaskConsumer> selected = new ArrayList<ITaskConsumer>();
        
        for (ITaskConsumer consumer:matching)
            if ( consumer.getWaitingCount() == min )
                selected.add( consumer );
        
        return selected;
    }

    /************************************************************************
     *  
     *
     * @param matching
     * @return
     */
    private int 
    findMin(List<ITaskConsumer> matching)
    {
        int min = Integer.MAX_VALUE;
        
        for (ITaskConsumer consumer:matching)
            if ( consumer.getWaitingCount() < min )
                min = consumer.getWaitingCount();
        
        return min;
    }

    /************************************************************************
     *  
     *
     * @param size
     * @return
     */
    private int 
    generateRandomIndex(int size)
    {
        Random random = new SecureRandom();
        
        return random.nextInt( size );
    }

}

// ##########################################################################
