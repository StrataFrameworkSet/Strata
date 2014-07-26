// ##########################################################################
// # File Name:	SpringClientFactory.java
// #
// # Copyright:	2011, Stratagema Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the Strata Analyzer Framework.
// #
// #   			The Strata Analyzer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The Strata Analyzer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the Strata Analyzer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// #
// #			You can obtain support for the Strata Analyzer 
// #			Framework at http://www.stratagemasystems.com/strata/.
// ##########################################################################

package strata1.client.springclient;

import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
import strata1.client.bootstrap.AbstractClientFactory;
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.common.authentication.IAuthenticator;
import strata1.common.logger.ILogger;
import java.util.List;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringClientFactory
    extends AbstractClientFactory
{

    /************************************************************************
     * Creates a new SpringClientFactory. 
     *
     */
    public 
    SpringClientFactory() {}

    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger createLogger()
    {
        // TODO Auto-generated method stub
        return null;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer()
    {
        return null;
        /*
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext())); */
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer(String resourceLocation)
    {
        return null;
        /*
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericXmlApplicationContext(resourceLocation)));
                    */
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IModule> 
    createModules()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRegionManager 
    createRegionManager()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    createDispatcher()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILoginView 
    createLoginView(IDispatcher dispatcher)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISplashView 
    createSplashView(IDispatcher dispatcher)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IAuthenticator 
    createAuthenticator()
    {
        // TODO Auto-generated method stub
        return null;
    }

}


// ##########################################################################
