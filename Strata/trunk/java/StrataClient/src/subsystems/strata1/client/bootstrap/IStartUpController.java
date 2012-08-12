// ##########################################################################
// # File Name:	IStartUpController.java
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

package strata1.client.bootstrap;

import strata1.interactor.controller.IController;
import strata1.interactor.view.ILoginView;
import strata1.interactor.view.ISplashView;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.logger.ILogger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IStartUpController
    extends IController
{
    public void
    setLogger(ILogger logger);
    
    public void
    setContainer(IClientContainer container);
    
    public void
    setLoginView(ILoginView loginView);
    
    public void
    setSplashView(ISplashView splashView);
    
    public void 
    setAuthenticator(IClientAuthenticator authenticator);
    
    public void
    setInitializer(Runnable initializer);
    
    public void
    setActivator(Runnable activator);
    
    public void
    setInitializationMessage(String message);
    
    public void
    setInitializationIncrements(int increments);

    public void
    resetInitializationProgress();
    
    public void
    incrementInitializationProgress();
}

// ##########################################################################
