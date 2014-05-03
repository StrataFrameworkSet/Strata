// ##########################################################################
// # File Name:	TaskConsumer.java
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

import strata1.common.logger.ILogger;
import strata1.common.producerconsumer.AbstractConsumer;
import strata1.common.producerconsumer.BlockingCollectionClosedException;
import strata1.common.producerconsumer.BlockingCollectionCompletedException;
import strata1.common.producerconsumer.BlockingQueue;
import strata1.common.producerconsumer.IBlockingCollection;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TaskConsumer
    extends 
        AbstractConsumer<ITask,ITaskConsumer,ITaskRouter,ITaskSelector>
    implements 
        ITaskConsumer
{
    private ITaskSelector              itsSelector;
    private IBlockingCollection<ITask> itsWaiting;
    private ILogger                    itsLogger;
    private boolean                    itsContinueLoop;
    private Thread                     itsConsumer;
    
    private static final int WARNING_LIMIT = 25;
    
    /************************************************************************
     * Creates a new {@code TaskConsumer}. 
     *
     */
    public 
    TaskConsumer(ITaskSelector selector,ILogger logger)
    {
        itsSelector     = selector;
        itsWaiting      = new BlockingQueue<ITask>();
        itsLogger       = logger;
        itsContinueLoop = true;
        itsConsumer     = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setSelector(ITaskSelector selector)
    {
        itsSelector = selector;
    }

    /************************************************************************
     * {@inheritDoc} 
     * @throws InterruptedException 
     * @throws BlockingCollectionCompletedException 
     * @throws BlockingCollectionClosedException 
     */
    @Override
    public void 
    putElement(ITask task) 
        throws 
            BlockingCollectionClosedException, 
            BlockingCollectionCompletedException, 
            InterruptedException
    {
        itsWaiting.putElement( task );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ITaskSelector 
    getSelector()
    {
        return itsSelector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getWaitingCount()
    {
        return itsWaiting.getElementCount();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startConsuming()
    {
        if ( itsConsumer != null )
        {
            itsLogger.logWarning( 
                "TaskConsumer is already consuming tasks. " +
                "Ignoring redundant start request." );
            return;
        }
        
        itsConsumer = new Thread(
            new Runnable()
            {
                @Override
                public void 
                run()
                {
                    doConsumeTaskLoop();
                }
                
            });
        itsConsumer.start();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopConsuming()
    {
        synchronized (this)
        {
            itsContinueLoop = false;
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isConsuming()
    {
        return
            itsConsumer != null &&
            itsConsumer.getState() == Thread.State.RUNNABLE;
    }

    /************************************************************************
     *  
     *
     */
    protected void
    doConsumeTaskLoop()
    {
        try
        {
            while ( continueLoop() )
            {
                checkWaitingCount();
                consumeTask();
            }
            
            while ( hasTasks() )
                consumeTask();
        }
        catch(BlockingCollectionCompletedException e)
        {
        }
        catch(Exception e)
        {
            itsLogger.logError( "This should not happen: " + e.getMessage() );
        }
        finally
        {
            itsLogger.logInfo( "Stopping TaskConsumer." );
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    continueLoop()
    {
        synchronized (this)
        {
            return itsContinueLoop;
        }
    }

    /************************************************************************
     *  
     *
     */
    private void 
    checkWaitingCount()
    {
        if ( getWaitingCount() >= WARNING_LIMIT )
            itsLogger
                .logWarning( 
                    "TaskConsumer has " + getWaitingCount() + 
                    " tasks waiting to be processed." );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    hasTasks()
    {
        return !itsWaiting.isCompleted();
    }

    /************************************************************************
     *  
     *
     * @throws BlockingCollectionCompletedException
     * @throws InterruptedException
     */
    private void 
    consumeTask() 
        throws 
            BlockingCollectionCompletedException, 
            InterruptedException
    {
        try
        {
            itsLogger.logVerbose( "Consuming task." );
            itsWaiting
                .takeElement()
                .execute();
        }
        catch(BlockingCollectionCompletedException e)
        {
            throw e;
        }
        catch(InterruptedException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            itsLogger.logError(  "Caught exception during consumeTask: " + e.getMessage() );
        }
    }
}

// ##########################################################################
