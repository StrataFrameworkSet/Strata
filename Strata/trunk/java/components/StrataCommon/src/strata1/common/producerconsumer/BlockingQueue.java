// ##########################################################################
// # File Name:	BlockingQueue.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

import strata1.common.utility.ISynchronizer;
import strata1.common.utility.ReadWriteLockSynchronizer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BlockingQueue<T>
    implements IBlockingCollection<T>
{
    private java.util.concurrent.BlockingQueue<T> itsImp;
    private BlockingCollectionState               itsState;
    private ISynchronizer                         itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code BlockingCollection}. 
     *
     */
    public 
    BlockingQueue()
    {
        itsImp          = new java.util.concurrent.LinkedBlockingQueue<T>();
        itsState        = BlockingCollectionState.OPEN;
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    putElement(T element) 
        throws 
            BlockingCollectionClosedException,
            BlockingCollectionCompletedException,
            InterruptedException
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            switch ( itsState )
            {
            case OPEN:
                itsImp.put( element );
                break;
                
            case CLOSED:
                throw new BlockingCollectionClosedException();
                
            case COMPLETED:
                throw new BlockingCollectionCompletedException();
            }
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
    public T 
    takeElement() 
        throws 
            BlockingCollectionCompletedException,
            InterruptedException
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            switch ( itsState )
            {
            case OPEN:
                return itsImp.take();
                
            case CLOSED:
                T element = null;
                
                if ( itsImp.isEmpty() )
                    throw new BlockingCollectionCompletedException();
                
                element = itsImp.take();
                
                if ( itsImp.isEmpty() )
                    itsState = BlockingCollectionState.COMPLETED;
                
                return element;
                
            case COMPLETED:                
            default:
                throw new BlockingCollectionCompletedException();
            }
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
    close()
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            switch ( itsState )
            {
            case OPEN:
            case CLOSED:
                if ( itsImp.isEmpty() )
                    itsState = BlockingCollectionState.COMPLETED;
                else
                    itsState = BlockingCollectionState.CLOSED;
                break;
                  
            case COMPLETED:
                break;
            }
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
    public boolean 
    isClosed()
    {
        try
        {
            itsSynchronizer.lockForReading();
            return itsState == BlockingCollectionState.CLOSED;
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
    isCompleted()
    {
        try
        {
            itsSynchronizer.lockForReading();
            return itsState == BlockingCollectionState.CLOSED;
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

}

// ##########################################################################
