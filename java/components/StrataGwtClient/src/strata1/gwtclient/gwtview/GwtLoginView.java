// ##########################################################################
// # File Name:	GwtLoginView.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtClient Framework.
// #
// #   			The StrataGwtClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.gwtview;

import strata1.client.command.ILoginProvider;
import strata1.client.view.ILoginView;
import strata1.common.authentication.ICredential;
import strata1.common.authentication.UserNameAndPasswordCredential;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtLoginView
    extends    AbstractGwtView<ILoginProvider>
    implements ILoginView
{
    private VerticalPanel   verticalPanel;
    
    private HorizontalPanel row1;
    private Label           userNameLabel;
    private TextBox         userNameField;
    
    private HorizontalPanel row2;
    private Label           passwordLabel;
    private TextBox         passwordField;
    
    private HorizontalPanel row3;
    private Button          loginButton;
    private Button          cancelButton;
    
    private HorizontalPanel row4;
    private Label           errorLabel;
    
    /************************************************************************
     * Creates a new {@code GwtLoginView}. 
     *
     */
    public 
    GwtLoginView(String viewName) 
    {
        super( viewName );
        verticalPanel = new VerticalPanel();
        initWidget(verticalPanel);
        
        row1 = new HorizontalPanel();
        verticalPanel.add(row1);
        
        userNameLabel = new Label("User Name:");
        row1.add(userNameLabel);
        
        userNameField = new TextBox();
        row1.add(userNameField);
        
        row2 = new HorizontalPanel();
        verticalPanel.add(row2);
        
        passwordLabel = new Label("Password:");
        row2.add(passwordLabel);
        
        passwordField = new TextBox();
        row2.add(passwordField);
        
        row3 = new HorizontalPanel();
        verticalPanel.add(row3);
        
        loginButton = new Button("Login");
        row3.add(loginButton);
        
        cancelButton = new Button("Cancel");
        row3.add(cancelButton);
        
        row4 = new HorizontalPanel();
        verticalPanel.add(row4);
        
        errorLabel = new Label("");
        row4.add(errorLabel);
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
    setInvalidUserName()
    {
        userNameField.setFocus( true );
        userNameField.selectAll();
        setLoginError( "Invalid user name" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidPassword()
    {
        passwordField.setFocus( true );
        passwordField.selectAll();
        setLoginError( "Invalid password" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginError(String message)
    {
        errorLabel.setText( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getUserName()
    {
        return userNameField.getText();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPassword()
    {
        return passwordField.getText();
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

}

// ##########################################################################
