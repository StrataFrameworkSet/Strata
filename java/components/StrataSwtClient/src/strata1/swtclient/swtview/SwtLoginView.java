// ##########################################################################
// # File Name:	SwtLoginView.java
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

package strata1.swtclient.swtview;

import strata1.client.command.ExecutionException;
import strata1.client.shell.IDispatcher;
import strata1.client.view.AbstractView;
import strata1.client.view.ILoginView;
import strata1.swtclient.swtshell.ISwtDispatcher;
import strata1.common.authentication.ICredential;
import strata1.common.authentication.UserNameAndPasswordCredential;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.SWTResourceManager;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtLoginView
    extends     AbstractView
    implements  ILoginView,
                ISwtView
{
    private ISwtDispatcher itsDispatcher;
    private Shell          itsShell;
    private Composite      itsBanner;
    private Label          itsSystemLabel;

    private Composite      itsControls;
    private Label          itsUserNameLabel;
    private Text           itsUserNameField;
    private Label          itsPasswordLabel;
    private Text           itsPasswordField;
    private Label          itsEnvironmentLabel;
    private Combo          itsEnvironmentCombo;
    
    private Button         itsLoginButton;
    private Button         itsCancelButton;
    private Label          itsLoginErrorLabel;

    /************************************************************************
     * Creates a new {@code SwtLoginView}. 
     * @wbp.parser.entryPoint
     *
     */
    public 
    SwtLoginView(IDispatcher dispatcher,String appName)
    {
        itsDispatcher = (ISwtDispatcher)dispatcher;
        
        createShell( appName );
        createBanner( appName );
        createControls();
        centerShell();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        itsShell.open();
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
        itsShell.close();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        itsShell.setVisible( true );
        itsUserNameField.setFocus();
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        itsShell.setVisible( false );
        itsDispatcher.processWork();
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

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Composite 
    getComposite()
    {
        return itsShell;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidUserName()
    {
        itsUserNameField.setFocus();
        itsUserNameField.selectAll();
        setLoginError( "Invalid user name." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidPassword()
    {
        itsPasswordField.setFocus();
        itsPasswordField.selectAll();
        setLoginError( "Invalid password." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginError(String message)
    {
        itsLoginErrorLabel.setText( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getUserName()
    {
        return itsUserNameField.getText();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPassword()
    {
        return itsPasswordField.getText();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICredential 
    getCredential()
    {
        return 
            new UserNameAndPasswordCredential(getUserName(),getPassword());
    }

    /************************************************************************
     *  
     *
     * @param appName
     */
    private void 
    createShell(String appName)
    {
        GridLayout shellLayout = null;
        
        itsShell = 
            new Shell( itsDispatcher.getDisplay(),SWT.NONE);
        itsShell.setSize(449, 204);
        itsShell.setMinimumSize(new Point(300, 275));
       
        itsShell.setBackground(
            SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
        itsShell.setText( appName );
        shellLayout = new GridLayout(1, true);
        shellLayout.horizontalSpacing = 10;
        itsShell.setLayout(shellLayout);
    }

    /************************************************************************
     *  
     *
     * @param appName
     */
    private void 
    createBanner(String appName)
    {
        GridData bannerGridData = null;
        
        itsBanner = new Composite(itsShell, SWT.NONE);
        itsBanner.setFont(
            SWTResourceManager.getFont("Segoe UI", 14, SWT.BOLD));
        itsBanner.setLayout(new FillLayout(SWT.HORIZONTAL));
        bannerGridData = new GridData(
            SWT.CENTER, SWT.BOTTOM, false, false, 1, 1);
        bannerGridData.heightHint = 100;
        bannerGridData.widthHint = 435;
        itsBanner.setLayoutData(bannerGridData);
        itsBanner.setBackgroundMode( SWT.INHERIT_DEFAULT );
        itsBanner.setBackgroundImage( 
            SWTResourceManager.getImage( 
                "C:/Users/Public/Pictures/Sample Pictures/Chrysanthemum.jpg" ) );
        
        itsSystemLabel = new Label(itsBanner, SWT.CENTER);
        itsSystemLabel.setAlignment(SWT.CENTER);
        itsSystemLabel.setForeground(
            SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        itsSystemLabel.setFont(
            SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
        itsSystemLabel.setText(appName);
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createControls()
    {
        createComposite();      
        
        createUserNameField();
        createPasswordField(); 
        createEnvironmentField();
        
        new Label(itsControls, SWT.NONE);
        new Label(itsControls, SWT.NONE);
        new Label(itsControls, SWT.NONE);
        
        createButtons();
        createLoginErrorField();
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createComposite()
    {
        GridData controlGridData = null;
        
        itsControls = new Composite(itsShell, SWT.NONE);
        itsControls.setBackgroundMode( SWT.INHERIT_DEFAULT );
        itsControls.setBackground(
            SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
        itsControls.setLayout(new GridLayout(2, false));
        
        controlGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        itsControls.setLayoutData(controlGridData);
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createUserNameField()
    {
        GridData userNameLabelGridData = null;
        
        itsUserNameLabel = new Label(itsControls, SWT.HORIZONTAL | SWT.CENTER);
        itsUserNameLabel.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsUserNameLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
        itsUserNameLabel.setAlignment(SWT.RIGHT);
        itsUserNameLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        userNameLabelGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
        userNameLabelGridData.heightHint = 19;
        userNameLabelGridData.widthHint = 98;
        itsUserNameLabel.setLayoutData(userNameLabelGridData);
        itsUserNameLabel.setText("User Name");
        itsUserNameField = new Text(itsControls, SWT.BORDER);
        itsUserNameField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createPasswordField()
    {
        itsPasswordLabel = new Label(itsControls, SWT.NONE);
        itsPasswordLabel.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsPasswordLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
        itsPasswordLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        GridData gd_itsPasswordLabel = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
        gd_itsPasswordLabel.heightHint = 20;
        itsPasswordLabel.setLayoutData(gd_itsPasswordLabel);
        itsPasswordLabel.setText("Password");
        
        itsPasswordField = new Text(itsControls, SWT.PASSWORD|SWT.BORDER);
        itsPasswordField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        itsPasswordField.setEchoChar( '*' );
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createEnvironmentField()
    {
        itsEnvironmentLabel = new Label(itsControls, SWT.NONE);
        itsEnvironmentLabel.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsEnvironmentLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        itsEnvironmentLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        itsEnvironmentLabel.setText("Environment");
        
        itsEnvironmentCombo = new Combo(itsControls, SWT.NONE);
        itsEnvironmentCombo.setItems(new String[] {"Development", "Test", "Production"});
        itsEnvironmentCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
        itsEnvironmentCombo.select(1);
        
        itsEnvironmentLabel.setVisible( false );
        itsEnvironmentCombo.setVisible( false );
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createButtons()
    {
        Composite composite_1 = new Composite(itsControls, SWT.NONE);
        composite_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        composite_1.setForeground(SWTResourceManager.getColor(102, 51, 51));
        composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
        GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
        gd_composite_1.widthHint = 322;
        gd_composite_1.heightHint = 30;
        composite_1.setLayoutData(gd_composite_1);
        
        itsLoginButton = new Button(composite_1, SWT.NONE);
        itsLoginButton.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsLoginButton.setText("Login");
        itsLoginButton.addSelectionListener(
            new SelectionAdapter() 
            {
                @Override
                public void 
                widgetSelected(SelectionEvent arg0) 
                {
                    try
                    {
                        invoke( "Login" );
                    }
                    catch(ExecutionException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } );
        
        itsCancelButton = new Button(composite_1, SWT.NONE);
        itsCancelButton.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsCancelButton.setText("Cancel");
        
        itsCancelButton.addSelectionListener(
            new SelectionAdapter() 
            {
                @Override
                public void 
                widgetSelected(SelectionEvent arg0) 
                {
                    try
                    {
                        invoke( "Cancel" );
                    }
                    catch(ExecutionException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } );
    }

    private void
    createLoginErrorField()
    {
        GridData loginErrorGridData = null;
        
        itsLoginErrorLabel = new Label(itsControls, SWT.CENTER);
        itsLoginErrorLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        itsLoginErrorLabel.setForeground(SWTResourceManager.getColor(128, 0, 0));
        loginErrorGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        loginErrorGridData.heightHint = 19;
        itsLoginErrorLabel.setLayoutData(loginErrorGridData);
        itsLoginErrorLabel.setText( "" );

    }
    
    /************************************************************************
     *  
     *
     */
    private void centerShell()
    {
        itsShell.pack();
    
        Monitor primary = itsDispatcher.getDisplay().getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = itsShell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        itsShell.setLocation(x, y);
    }
}

// ##########################################################################
