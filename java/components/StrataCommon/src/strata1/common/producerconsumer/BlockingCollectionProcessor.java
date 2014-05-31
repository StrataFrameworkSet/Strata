// ##########################################################################
// # File Name:	BlockingCollectionProcessor.java
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
import java.util.concurrent.atomic.AtomicBoolean;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public
class BlockingCollectionProcessor<T>
    implements Runnable
{
    private final BlockingQueue<T> itsSource;
    private final IConsumer<T>     itsConsumer;
    private final AtomicBoolean    itsContinueFlag;
    
    /************************************************************************
     * Creates a new {@code BlockingCollectionProcessor}. 
     *
     */
    public 
    BlockingCollectionProcessor(
        BlockingQueue<T> source,
        IConsumer<T>     consumer,
        AtomicBoolean    continueFlag)
    {
        itsSource       = source;
        itsConsumer     = consumer;
        itsContinueFlag = continueFlag;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run()
    {
        while (itsContinueFlag.get())
        {
            try
            {
                itsConsumer.consume( itsSource.take() );
            }
            catch(InterruptedException e)
            {
            }
        }
    }
    
    /************************************************************************
     *  
     *
     */
    public void
    shutdown()
    {
        itsContinueFlag.compareAndSet( true,false );
    }

}

// ##########################################################################
