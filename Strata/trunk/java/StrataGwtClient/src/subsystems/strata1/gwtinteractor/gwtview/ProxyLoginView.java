// ##########################################################################
// # File Name:	ProxyLoginView.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtInteractor Framework.
// #
// #   			The StrataGwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtinteractor.gwtview;

import strata1.gwtinteractor.updateclient.UpdateResponse;
import strata1.gwtinteractor.updateserver.IUpdatableManager;
import strata1.client.command.ICommandInvokerManager;
import strata1.client.view.ILoginView;
import strata1.common.authentication.IClientCredential;
import strata1.common.authentication.UserNameAndPasswordCredential;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ProxyLoginView
    extends    AbstractProxyView
    implements ILoginView
{
    private String itsUserName;
    private String itsPassword;
    
    /************************************************************************
     * Creates a new {@code ProxyLoginView}. 
     *
     * @param invokerManager
     * @param updatableManager
     */
    public 
    ProxyLoginView(
        ICommandInvokerManager invokerManager,
        IUpdatableManager      updatableManager)
    {
        super( "LoginView",invokerManager,updatableManager );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidUserName()
    {
        UpdateResponse response = new UpdateResponse(getViewName());
        
        response.setUpdateAction( "setInvalidUserName" );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidPassword()
    {
        UpdateResponse response = new UpdateResponse(getViewName());
        
        response.setUpdateAction( "setInvalidPassword" );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginError(String message)
    {
        UpdateResponse response = new UpdateResponse(getViewName());
        
        response.setUpdateAction( "setLoginError" );
        response.insertUpdatedProperty( "LoginError",message );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getUserName()
    {
        return itsUserName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPassword()
    {
        return itsPassword;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientCredential 
    getCredential()
    {
        return 
            new UserNameAndPasswordCredential(getUserName(),getPassword());
    }

}

// ##########################################################################
