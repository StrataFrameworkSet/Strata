// ##########################################################################
// # File Name:	SwtGreetingModule.java
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

package strata1.client.helloworld;

import strata1.client.bootstrap.IClientBootstrapper;
import strata1.client.bootstrap.IClientContainer;
import strata1.client.bootstrap.IClientModule;
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.swthelloworld.SwtGreetingView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class GreetingModule
    implements IClientModule
{

    /************************************************************************
     * Creates a new {@code SwtGreetingModule}. 
     *
     */
    public 
    GreetingModule()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initialize(IClientBootstrapper bootstrapper)
    {
        IClientContainer      container  = bootstrapper.getContainer();
        IRegionManager        manager    = bootstrapper.getRegionManager();
        IDispatcher           dispatcher = bootstrapper.getDispatcher();
        
        IHelloWorldModel      model;
        IHelloWorldView       view;
        IHelloWorldController controller;
        
        model      = new HelloWorldModel();
        view       = createHelloWorldView( dispatcher );
        controller = new HelloWorldController( model,view );
        
        manager.registerWithRegion( "Greeting",SwtGreetingView.class );
  
        container.registerModel( model );
        container.registerView( view );
        container.registerController( "MainController",controller );
    }
    
    protected abstract IHelloWorldView
    createHelloWorldView(IDispatcher dispatcher);

}

// ##########################################################################
