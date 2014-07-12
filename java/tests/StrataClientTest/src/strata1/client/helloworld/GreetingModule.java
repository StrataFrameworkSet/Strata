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

import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.injector.container.AbstractModule;
import strata1.injector.container.Binder;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
//import strata1.client.swthelloworld.SwtGreetingView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class GreetingModule
    extends AbstractModule
    implements IModule
{
 
    /************************************************************************
     * Creates a new {@code SwtGreetingModule}. 
     *
     */
    public 
    GreetingModule(String name)
    {
        super(name);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initialize(IContainer container)
    {
        IRegionManager  manager    = null;
        IDispatcher     dispatcher = null;
        
        IHelloWorldModel      model      = null;
        IHelloWorldView       view       = null;
        IHelloWorldController controller = null;
        
        manager    = container.getInstance(IRegionManager.class);
        dispatcher = container.getInstance(IDispatcher.class);
        model      = new HelloWorldModel();
        view       = createHelloWorldView( dispatcher );
        controller = new HelloWorldController( model,view );
        
        //manager.registerWithRegion( "Greeting",SwtGreetingView.class );
  
        container
            .insertBinding( 
                bindType( IHelloWorldModel.class )
                    .toInstance(model) )
            .insertBinding( 
                bindType( IHelloWorldView.class )
                    .toInstance(view) )
            .insertBinding( 
                bindType( IHelloWorldController.class )
                    .withKey( "MainController" )
                    .toInstance(controller) );
    }
    
    protected abstract IHelloWorldView
    createHelloWorldView(IDispatcher dispatcher);

}

// ##########################################################################
