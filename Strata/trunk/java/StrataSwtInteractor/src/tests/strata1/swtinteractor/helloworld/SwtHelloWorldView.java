// ##########################################################################
// # File Name:	SwtHelloWorldView.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.swtinteractor.helloworld;

import strata1.interactor.command.Command;
import strata1.swtinteractor.swtview.SwtView;
import strata1.interactor.view.AbstractView;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtHelloWorldView
    extends    AbstractView
    implements HelloWorldView, SwtView
{
    private Display                             itsDisplay;
    private Shell                               itsShell;

    /************************************************************************
     * Creates a new SwtHelloWorldView. 
     *
     */
    public 
    SwtHelloWorldView()
    {
        super();
        itsDisplay   = new Display();
        itsShell     = new Shell( itsDisplay );
        
        itsShell.setText("Hello World");
        itsShell.setSize(550, 327);
        itsShell.setLayout(new GridLayout(1, false));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    start()
    {
        itsShell.open();
        while (!itsShell.isDisposed()) 
        {
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
        System.exit( 0 );
    }
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        itsShell.setVisible( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        itsShell.setVisible( false );
    }

     /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    invoke(Command command)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Widget 
    getWidget()
    {
        return itsShell;
    }

    @Override
    public Composite 
    getComposite()
    {
        return itsShell;
    }

}


// ##########################################################################
