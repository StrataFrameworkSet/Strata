// ##########################################################################
// # File Name:	SwtHelloWorldClientBootstrapper.java
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

package strata1.client.swthelloworld;

import strata1.client.bootstrap.AbstractClientBootstrapper;
import strata1.client.helloworld.IHelloWorldController;
import strata1.swtclient.swtregion.SwtRegion;
import strata1.swtclient.swtregion.SwtRegionManager;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtHelloWorldClientBootstrapper
    extends AbstractClientBootstrapper
{

    /************************************************************************
     * Creates a new {@code SwtHelloWorldClientBootstrapper}. 
     *
     */
    public 
    SwtHelloWorldClientBootstrapper()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    configureModules()
    {
        getModuleManager().registerModule( new SwtGreetingModule() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    configureRegionManager()
    {
        SwtRegion.setManager( (SwtRegionManager)getRegionManager() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    startApplication()
    {
        IHelloWorldController mainController = 
            getContainer()
                .getInstance( 
                    IHelloWorldController.class,
                    "MainController" );
        
        mainController.start();
    }
}

// ##########################################################################
