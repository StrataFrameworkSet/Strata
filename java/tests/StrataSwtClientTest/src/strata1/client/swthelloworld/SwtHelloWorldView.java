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

package strata1.client.swthelloworld;

import strata1.client.command.ExecutionException;
import strata1.client.helloworld.IHelloWorldProvider;
import strata1.client.helloworld.IHelloWorldView;
import strata1.client.region.RegionInitializationException;
import strata1.client.shell.IDispatcher;
import strata1.client.view.AbstractView;
import strata1.swtclient.swtshell.ISwtDispatcher;
import strata1.swtclient.swtview.ISwtView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
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
    extends    AbstractView<IHelloWorldProvider>
    implements IHelloWorldView, ISwtView
{
    private ISwtDispatcher                      itsDispatcher;
    private Shell                               itsShell;
    private Menu                                itsMenuBar;
    private MenuItem                            itsFileMenuItem;
    private Menu                                itsFileMenu;
    private MenuItem                            itsExitMenuItem;
    private Composite                           itsPanel;
    private Label                               itsGreetingLabel;

    /************************************************************************
     * Creates a new SwtHelloWorldView. 
     * @throws RegionInitializationException 
     *
     */
    public 
    SwtHelloWorldView(IDispatcher dispatcher) 
    {
        itsDispatcher = (ISwtDispatcher)dispatcher;
        
        itsShell = new Shell( itsDispatcher.getDisplay() );
        itsShell.addShellListener(
            new ShellAdapter() 
            {
                @Override
                public void 
                shellClosed(ShellEvent arg0) 
                {
                    System.exit( 0 );
                }
            });
        itsShell.setText("Hello World");
        itsShell.setSize(550, 327);
        itsShell.setLayout(new GridLayout(1, false));
        
        itsMenuBar      = new Menu( itsShell,SWT.BAR );
        itsFileMenuItem = new MenuItem( itsMenuBar,SWT.CASCADE );
        itsFileMenu     = new Menu( itsShell,SWT.DROP_DOWN );
        itsExitMenuItem = new MenuItem( itsFileMenu,SWT.PUSH );
        
        itsShell.setMenuBar( itsMenuBar );
        itsFileMenuItem.setMenu( itsFileMenu );
        itsFileMenuItem.setText( "File" );
        itsExitMenuItem.setText( "Exit" );
        
        itsExitMenuItem.addSelectionListener( 
            new SelectionAdapter() 
            {
                @Override
                public void 
                widgetSelected(SelectionEvent e)
                {
                    try
                    {
                        getProvider().getExitCommand().execute();
                    }
                    catch (ExecutionException ex)
                    {
                        ex.printStackTrace();
                    }
                }
            } );

        itsPanel         = new Composite( itsShell,SWT.NONE);
        itsGreetingLabel = new Label( itsPanel,SWT.NONE );
        itsGreetingLabel.setSize(100, 20);
        itsGreetingLabel.setText("Hello?");
        
        Monitor primary = itsDispatcher.getDisplay().getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = itsShell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        itsShell.setLocation(x, y);

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
        itsShell.close();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        itsPanel.setVisible( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        itsPanel.setVisible( false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setGreeting(String greeting)
    {
        itsGreetingLabel.setText( greeting );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Widget 
    getWidget()
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Composite 
    getComposite()
    {
        return null;
    }

}


// ##########################################################################
