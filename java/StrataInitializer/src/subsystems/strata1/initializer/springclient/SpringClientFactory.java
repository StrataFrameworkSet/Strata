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

package strata1.initializer.springclient;

import strata1.initializer.client.AbstractClientFactory;
import strata1.initializer.client.ClientContainer;
import strata1.initializer.client.IClientContainer;
import strata1.initializer.client.IClientModuleManager;
import strata1.initializer.springprovider.SpringContainerProvider;
import strata1.interactor.region.IRegionManager;
import strata1.interactor.shell.IDispatcher;
import strata1.interactor.shell.IShell;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

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
    public IClientContainer 
    createContainer()
    {
        return 
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext()));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
    createContainer(String resourceLocation)
    {
        return 
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericXmlApplicationContext(resourceLocation)));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientModuleManager 
    createModuleManager()
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
    public IShell 
    createShell(IDispatcher dispatcher)
    {
        // TODO Auto-generated method stub
        return null;
    }

}


// ##########################################################################
