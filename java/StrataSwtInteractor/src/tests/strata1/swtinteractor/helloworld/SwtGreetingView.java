// ##########################################################################
// # File Name:	SwtGreetingView.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.swtinteractor.helloworld;

import strata1.swtinteractor.swtview.SwtView;
import strata1.interactor.command.Command;
import strata1.interactor.command.CommandProvider;
import strata1.interactor.event.ChangeEvent;
import strata1.interactor.view.AbstractView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class SwtGreetingView
    extends AbstractView
    implements GreetingView, SwtView
{
    private Label itsGreeting;
    
    /************************************************************************
     * Creates a new {@code SwtGreetingView}. 
     *
     */
    public SwtGreetingView(Composite parent)
    {
        itsGreeting = new Label( parent,SWT.NONE );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void start()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void stop()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void show()
    {
        itsGreeting.setVisible( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void hide()
    {
        itsGreeting.setVisible( false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void invoke(Command command)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void displayGreeting(String greeting)
    {
        itsGreeting.setText( greeting );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Widget getWidget()
    {
        return itsGreeting;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Composite getComposite()
    {
        return null;
    }

}

// ##########################################################################
