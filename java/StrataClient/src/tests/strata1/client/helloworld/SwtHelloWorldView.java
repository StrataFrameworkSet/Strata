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

package strata1.client.helloworld;

import strata1.swtinteractor.swtregion.SwtRegion;
import strata1.swtinteractor.swtshell.SwtShell;
import strata1.swtinteractor.swtview.ISwtView;
import strata1.interactor.command.ICommand;
import strata1.interactor.region.IRegion;
import strata1.interactor.region.RegionInitializationException;
import strata1.interactor.shell.IShell;
import strata1.interactor.view.AbstractView;
import org.eclipse.swt.widgets.Composite;
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
    implements HelloWorldView, ISwtView
{
    private IRegion itsGreetingRegion;

    /************************************************************************
     * Creates a new SwtHelloWorldView. 
     * @throws RegionInitializationException 
     *
     */
    public 
    SwtHelloWorldView(IShell shell) 
    {
        itsGreetingRegion = 
            new SwtRegion( "Greeting",((SwtShell)shell).getShell() );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    start()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
    }
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        ((ISwtView)itsGreetingRegion.getView())
            .getComposite().setVisible( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        ((ISwtView)itsGreetingRegion.getView())
            .getComposite().setVisible( false );
    }

     /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    invoke(ICommand command)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Widget 
    getWidget()
    {
        return ((ISwtView)itsGreetingRegion.getView()).getWidget();
    }

    @Override
    public Composite 
    getComposite()
    {
        return ((ISwtView)itsGreetingRegion.getView()).getComposite();
    }

}


// ##########################################################################
