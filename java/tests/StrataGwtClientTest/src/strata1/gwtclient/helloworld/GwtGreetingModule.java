// ##########################################################################
// # File Name:	GwtGreetingModule.java
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

import strata1.client.helloworld.GreetingModule;
import strata1.client.helloworld.IHelloWorldView;
import strata1.gwtclient.gwtview.GwtLoginView;
import strata.presentation.shell.IDispatcher;
import strata.presentation.view.ILoginView;
import strata.presentation.view.ISplashView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtGreetingModule
    extends GreetingModule
{

    /************************************************************************
     * Creates a new {@code GwtGreetingModule}. 
     *
     * @param name
     */
    public 
    GwtGreetingModule()
    {
        super( "GwtGreetingModule" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected ILoginView 
    createLoginView(IDispatcher dispatcher)
    {
        return new GwtLoginView( "HelloWorldLogin" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected ISplashView 
    createSplashView(IDispatcher dispatcher)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IHelloWorldView 
    createHelloWorldView(IDispatcher dispatcher)
    {
        return new GwtHelloWorldView( "HelloWorld" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IDispatcher 
    createDispatcher()
    {
        return null;
    }

}

// ##########################################################################
