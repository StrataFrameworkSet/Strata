// ##########################################################################
// # File Name:	AbstractActiveProducer.java
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

package strata.foundation.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractActivePriorityProducer<T>
    extends AbstractPriorityProducer<T>
{
    private ExecutorService itsExecutor;
    
    /************************************************************************
     * Creates a new {@code AbstractActiveProducer}. 
     *
     */
    public 
    AbstractActivePriorityProducer()
    {
        itsExecutor = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProducing()
    {
        if ( !isProducing() )
        {
            itsExecutor = Executors.newFixedThreadPool( 3 );
            itsExecutor.execute( getRunnable() );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopProducing()
    {
        stopRunnable();
        
        if ( itsExecutor != null )
            itsExecutor.shutdown();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isProducing()
    {
        return 
            (itsExecutor != null) &&
            (!itsExecutor.isTerminated());
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected abstract Runnable
    getRunnable();
    
    /************************************************************************
     *  
     *
     */
    protected void
    stopRunnable() {}
}

// ##########################################################################
