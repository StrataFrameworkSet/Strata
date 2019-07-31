// ##########################################################################
// # File Name:	GwtHelloWorldView.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtClientTest Framework.
// #
// #   			The StrataGwtClientTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtClientTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtClientTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.helloworld;

import strata1.client.helloworld.IHelloWorldProvider;
import strata1.client.helloworld.IHelloWorldView;
import strata1.gwtclient.gwtview.AbstractGwtView;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import strata.presentation.command.ExecutionException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtHelloWorldView
    extends    AbstractGwtView<IHelloWorldProvider>
    implements IHelloWorldView
{
    private VerticalPanel verticalPanel;
    private MenuBar       menuBar;
    private MenuBar       fileMenu;
    private MenuItem      fileMenuItem;
    private MenuItem      exitMenuItem;
    private Label         greetingLabel;
    private Command       exitCommand;
    
    public 
    GwtHelloWorldView(String viewName) 
    {
        super( viewName );
        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        
        menuBar = new MenuBar(false);
        menuBar.setAnimationEnabled(true);
        verticalPanel.add(menuBar);
        
        fileMenu = new MenuBar(true);
        fileMenu.setAnimationEnabled(true);
        
        fileMenuItem = new MenuItem("File", true, fileMenu);
        
        exitCommand = 
            new Command() 
            {
                @Override
                public void 
                execute()
                {
                    try
                    {
                        getProvider().getExitCommand().execute();
                    }
                    catch(ExecutionException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
            
        exitMenuItem = new MenuItem("Exit", true, exitCommand );
        fileMenu.addItem(exitMenuItem);
        menuBar.addItem( fileMenuItem );
        
        greetingLabel = new Label("Hello ?");
        verticalPanel.add(greetingLabel);
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        show();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        hide();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        verticalPanel.setVisible( true );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        verticalPanel.setVisible( false );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setGreeting(String greeting)
    {
        greetingLabel.setText( greeting );
    }

}

// ##########################################################################
