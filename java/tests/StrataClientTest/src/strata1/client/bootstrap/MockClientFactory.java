// ##########################################################################
// # File Name:	MockClientFactory.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

import strata1.common.authentication.IClientAuthenticator;
import strata1.common.logger.ILogger;
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MockClientFactory
    extends AbstractClientFactory
{

    /************************************************************************
     * Creates a new {@code MockClientFactory}. 
     *
     */
    public 
    MockClientFactory()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    createLogger()
    {
        return Mockito.mock( ILogger.class );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IModule> 
    createModules()
    {
        return Mockito.spy( new ArrayList<IModule>() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer()
    {
        return Mockito.mock( IContainer.class );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer(String resourceLocation)
    {
        return createContainer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRegionManager 
    createRegionManager()
    {
        return Mockito.mock( IRegionManager.class );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    createDispatcher()
    {
        return Mockito.mock( IDispatcher.class );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILoginView 
    createLoginView(IDispatcher dispatcher)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISplashView 
    createSplashView(IDispatcher dispatcher)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientAuthenticator 
    createAuthenticator()
    {
        return null;
    }

}

// ##########################################################################
