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

import strata1.common.authentication.FakeAuthenticator;
import strata1.common.logger.ILogger;
import strata1.client.controller.ILoginController;
import strata1.client.controller.LoginController;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.injector.container.AbstractModule;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
//import strata1.client.swthelloworld.SwtGreetingView;
import java.util.HashMap;

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
        ILogger         logger = null;
        IDispatcher     dispatcher = null;
        
        ILoginController      loginController  = null;
        ILoginView            loginView        = null;
        IHelloWorldModel      model            = null;
        IHelloWorldView       view             = null;
        IHelloWorldController controller       = null;
        
        dispatcher       = createDispatcher();
        
        loginController  = new LoginController();
        loginView        = createLoginView(dispatcher);
        
        model      = new HelloWorldModel();
        view       = createHelloWorldView( dispatcher );
        controller = new HelloWorldController( model,view );
        
        loginController.setContainer( container );
        loginController.setLoginView( loginView );
        loginController.setMainController( controller );
        
        logger = container.getInstance( ILogger.class );
        
        loginController.setLogger( logger );
        loginController.setAuthenticator(
            new FakeAuthenticator(
                new HashMap<String,String>()
                {
    
                    private static final long serialVersionUID = 1L;
                    
                    {
                        put("john","foobar");
                        put("nay","FOOBAR");
                    }
                }) );
        
        container
            .insertBinding( 
                bindType( IDispatcher.class )
                    .toInstance(dispatcher) )
            .insertBinding( 
                bindType( IHelloWorldModel.class )
                    .toInstance(model) )
            .insertBinding( 
                bindType( IHelloWorldView.class )
                    .toInstance(view) )
            .insertBinding( 
                bindType( ILoginController.class )
                    .toInstance(loginController) )
            .insertBinding( 
                bindType( IHelloWorldController.class )
                    .toInstance(controller) );
    }
    
    protected abstract ILoginView
    createLoginView(IDispatcher dispatcher);
    
    protected abstract ISplashView
    createSplashView(IDispatcher dispatcher);
    
    protected abstract IHelloWorldView
    createHelloWorldView(IDispatcher dispatcher);

    protected abstract IDispatcher 
    createDispatcher();

}

// ##########################################################################
