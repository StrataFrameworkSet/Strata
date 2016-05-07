// ##########################################################################
// # File Name:	IClientFactory.java
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
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.common.authentication.IAuthenticator;
import strata1.common.commandline.ICommandLineParser;
import strata1.common.logger.ILogger;
import java.util.List;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IClientFactory
{
    /************************************************************************
     *  
     *
     * @return
     */
    public ICommandLineParser
    createCommandLineParser();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ILogger
    createLogger();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IContainer
    createContainer();

    /************************************************************************
     *  
     *
     * @param resourceLocation
     * @return
     */
    public IContainer
    createContainer(String resourceLocation);

    /************************************************************************
     *  
     *
     * @return
     */
    public List<IModule>
    createModules();

    /************************************************************************
     *  
     *
     * @return
     */
    public IRegionManager
    createRegionManager();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IDispatcher
    createDispatcher();
    
    /************************************************************************
     *  
     *
     * @param dispatcher
     * @return
     */
    public ILoginView
    createLoginView(IDispatcher dispatcher);
    
    /************************************************************************
     *  
     *
     * @param dispatcher
     * @return
     */
    public ISplashView
    createSplashView(IDispatcher dispatcher);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IAuthenticator
    createAuthenticator();
    
}

// ##########################################################################
