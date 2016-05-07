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

package strata1.swtclient.swtshell;

import strata1.client.shell.IDispatcher;
import org.eclipse.swt.widgets.Shell;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtShell
    implements ISwtShell
{
    private ISwtDispatcher itsDispatcher;
    private Shell          itsShell;

    /************************************************************************
     * Creates a new {@code SwtShell}. 
     *
     */
    public 
    SwtShell(ISwtDispatcher dispatcher)
    {
        itsDispatcher = dispatcher;
        itsShell      = new Shell( dispatcher.getDisplay() );

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        itsShell.open();
     }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        itsShell.dispose();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    getDispatcher()
    {
        return itsDispatcher;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Shell
    getShell()
    {
        return itsShell;
    }
}

// ##########################################################################
