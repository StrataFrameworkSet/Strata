// ##########################################################################
// # File Name:	SwtShell.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSwtInteractor Framework.
// #
// #   			The StrataSwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.swtinteractor.swtshell;

import strata1.interactor.shell.AbstractDispatcher;
import org.eclipse.swt.widgets.Display;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtDispatcher
    extends    AbstractDispatcher
    implements ISwtDispatcher
{
    private Display  itsDisplay;

    /************************************************************************
     * Creates a new {@code SwtShell}. 
     *
     */
    public 
    SwtDispatcher()
    {
        itsDisplay = new Display();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    invokeSynchronous(Runnable task)
    {
        itsDisplay.syncExec( task );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    invokeAsynchronous(Runnable task)
    {
        itsDisplay.asyncExec( task );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        while (!itsDisplay.isDisposed()) 
        {
            Runnable task = getNextTask();
                                               
            if ( task != null )
                invokeSynchronous( task );

            if (!itsDisplay.readAndDispatch()) 
                itsDisplay.sleep();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        itsDisplay.dispose();        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processWork()
    {
        if (!itsDisplay.isDisposed()) 
            while ( itsDisplay.readAndDispatch() );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Display
    getDisplay()
    {
        return itsDisplay;
    }
}

// ##########################################################################
