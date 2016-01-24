// ##########################################################################
// # File Name:	NullLoginView.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.view;

import strata1.client.command.ExecutionException;
import strata1.client.command.ILoginProvider;
import strata1.common.authentication.ICredential;
import strata1.common.authentication.UserNameAndPasswordCredential;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class NullLoginView
    extends    AbstractView<ILoginProvider>
    implements ILoginView
{

    /************************************************************************
     * Creates a new {@code NullLoginView}. 
     *
     */
    public 
    NullLoginView() {}

    /************************************************************************
     * {@inheritDoc} 
     * @throws ExecutionException 
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
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidUserName()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInvalidPassword()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginError(String message)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getUserName()
    {
        return "";
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPassword()
    {
        return "";
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICredential 
    getCredential()
    {
        return new UserNameAndPasswordCredential("","");
    }
}

// ##########################################################################
