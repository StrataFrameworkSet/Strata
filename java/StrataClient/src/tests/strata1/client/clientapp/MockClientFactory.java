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

package strata1.client.clientapp;

import strata1.interactor.region.IRegionManager;
import strata1.interactor.shell.IDispatcher;
import strata1.interactor.shell.IShell;
import strata1.client.clientapp.AbstractClientFactory;
import strata1.client.clientapp.IClientContainer;
import strata1.client.clientapp.IClientModuleManager;
import org.mockito.*;

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
    public IClientModuleManager 
    createModuleManager()
    {
        return Mockito.spy( super.createModuleManager() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
    createContainer()
    {
        return Mockito.mock( IClientContainer.class );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
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
    public IShell 
    createShell(IDispatcher dispatcher)
    {
        return Mockito.mock( IShell.class );
    }

}

// ##########################################################################
