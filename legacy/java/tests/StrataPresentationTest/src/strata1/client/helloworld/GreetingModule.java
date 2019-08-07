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

import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IModule;
import strata.foundation.logger.JavaLogEntryProcessor;
import strata.foundation.logger.Logger;
import strata.presentation.login.ILoginController;
import strata.presentation.login.LoginController;
import strata.presentation.shell.IDispatcher;
import strata.presentation.view.ILoginView;
import strata.presentation.view.ISplashView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class GreetingModule
    extends    AbstractModule
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
    initialize()
    {
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
        
        loginController.setLoginView( loginView );
        loginController.setMainController( controller );
        loginController.setLogger( 
            new Logger()
                .attachProcessor( new JavaLogEntryProcessor() ) );
        
        bindType( IDispatcher.class )
            .toInstance(dispatcher);
                
        bindType( IHelloWorldModel.class )
            .toInstance(model);
        
        bindType( IHelloWorldView.class )
            .toInstance(view);
        
        bindType( ILoginController.class )
            .toInstance(loginController);
        
        bindType( IHelloWorldController.class )
            .toInstance(controller);
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
