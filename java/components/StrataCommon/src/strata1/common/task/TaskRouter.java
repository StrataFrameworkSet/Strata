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
import strata1.common.producerconsumer.IConsumer;
import strata1.common.producerconsumer.IDispatcher;
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
    implements ITaskDispatcher
{
    private Set<IConsumer<ITask>> itsConsumers;
    private ISynchronizer         itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code TaskRouter}. 
     *
     */
    public 
    TaskRouter()
    {
        itsConsumers = new HashSet<IConsumer<ITask>>();
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher<ITask> 
    attachConsumer(IConsumer<ITask> consumer)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            if ( !hasConsumer( consumer ) )
                itsConsumers.add( consumer );
            
            return this;    
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
    public IDispatcher<ITask> 
    detachConsumer(IConsumer<ITask> consumer)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            if ( hasConsumer( consumer ) )
                itsConsumers.remove( consumer );
                
            return this;
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
    dispatch(ITask task)
    {
        try
        {
            IConsumer<ITask> consumer = null;
            
            itsSynchronizer.lockForReading();
            consumer = selectConsumer(task);
            consumer.consume(task);
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
    public void 
    startDispatching()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopDispatching()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isDispatching()
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(IConsumer<ITask> consumer)
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
    private IConsumer<ITask> 
    selectConsumer(ITask task)
    {
        List<IConsumer<ITask>> matching = new ArrayList<IConsumer<ITask>>();
        
        matching = filterOnSelector(itsConsumers,task);
        return matching.get( generateRandomIndex(matching.size()) );
    }

    /************************************************************************
     *  
     *
     * @param consumers
     * @param task
     * @return
     */
    private List<IConsumer<ITask>> 
    filterOnSelector(Set<IConsumer<ITask>> consumers,ITask task)
    {
        List<IConsumer<ITask>> selected = new ArrayList<IConsumer<ITask>>();
        
        for (IConsumer<ITask> consumer:consumers)
            if ( consumer.getSelector().match( task ) )
                selected.add( consumer );
        
        return selected;
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
        
        return size == 0 ? 0 : random.nextInt( size );
    }

}

// ##########################################################################
