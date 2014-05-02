// ##########################################################################
// # File Name:	Bootstrapper.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.bootstrap;

import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.commandline.ICommandLineProcessor;
import strata1.common.logger.ILogger;
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import java.util.List;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IClientBootstrapper
{
    public void
    setCommandLineProcessor(ICommandLineProcessor processor);
    
    public void
    setLogger(ILogger logger);
    
    public void 
    setModules(List<IModule> modules);

    public void 
    setContainer(IContainer container);
    
    public void
    setRegionManager(IRegionManager manager);
    
    public void
    setDispatcher(IDispatcher dispatcher);
    
    public void 
    setLoginView(ILoginView loginView);
    
    public void
    setSplashView(ISplashView splashView);
    
    public void 
    setAuthenticator(IClientAuthenticator authenticator);
    
    public ICommandLineProcessor
    getCommandLineProcessor();
    
    public ILogger
    getLogger();
    
    public List<IModule>
    getModules();

    public IContainer
    getContainer();
    
    public IRegionManager
    getRegionManager();
    
    public IDispatcher
    getDispatcher();
    
    public ILoginView
    getLoginView();
    
    public ISplashView
    getSplashView();
    
    public IClientAuthenticator
    getAuthenticator();
    
    public IStartUpController
    getController();
    
    public void
    run(IClientFactory factory,String[] arguments);
}


// ##########################################################################
