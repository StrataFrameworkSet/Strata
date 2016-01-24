// ##########################################################################
// # File Name:	LoginController.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.bootstrap;

import strata1.injector.container.Binder;
import strata1.injector.container.IContainer;
import strata1.client.command.ExecutionException;
import strata1.client.command.ICommand;
import strata1.client.command.ILoginProvider;
import strata1.client.controller.AbstractController;
import strata1.client.model.INullModel;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.common.authentication.AuthenticationFailureException;
import strata1.common.authentication.IAuthenticator;
import strata1.common.authentication.IPrincipal;
import strata1.common.logger.ILogger;
import strata1.common.logger.LoggingLevel;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StartUpController
    extends    AbstractController<ILoginProvider,ILoginView,INullModel>
    implements IStartUpController
{
    private ILogger              itsLogger;
    private IContainer           itsContainer;
    private ISplashView          itsSplashView;
    private IAuthenticator itsAuthenticator;
    private Runnable             itsInitializer;
    private Runnable             itsActivator;
    
    /************************************************************************
     * Creates a new {@code LoginController}. 
     *
     */
    public 
    StartUpController()
    {
        itsContainer     = null;
        itsSplashView    = null;
        itsAuthenticator = null;
        itsInitializer   = null;
        itsActivator     = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommand 
    getLoginCommand()
    {
        return 
            new ICommand()
            {
                @Override
                public void 
                execute() 
                    throws ExecutionException
                {
                    doLogin();
                } 
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommand 
    getCancelCommand()
    {
        return 
            new ICommand()
            {
                @Override
                public void 
                execute() 
                    throws ExecutionException
                {
                    doCancel();
                } 
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        getView().show();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLogger(ILogger logger)
    {
        itsLogger = logger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setContainer(IContainer container)
    {
        itsContainer = container;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginView(ILoginView loginView)
    {
        setView(loginView,this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setSplashView(ISplashView splashView)
    {
        itsSplashView = splashView;
        //itsSplashView.setProvider( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setAuthenticator(IAuthenticator authenticator)
    {
        itsAuthenticator = authenticator;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializer(Runnable initializer)
    {
        itsInitializer = initializer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setActivator(Runnable activator)
    {
        itsActivator = activator;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationIncrements(int increments)
    {
        itsSplashView.setInitializationIncrements( increments );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationMessage(String message)
    {
        itsSplashView.setMessage( message );
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    resetInitializationProgress()
    {
        itsSplashView.resetInitializationProgress();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    incrementInitializationProgress()
    {
        itsSplashView.incrementInitializationProgress();
    }

    /************************************************************************
     *  
     *
     */
    protected void
    doLogin()
    {
        itsLogger.log( LoggingLevel.INFO,"Logging in..." );
        
        try
        {
            itsContainer
                .insertBinding(  
                    Binder
                        .bindType( IPrincipal.class )
                        .toInstance( 
                            itsAuthenticator.authenticate( 
                                getView().getCredential() ) ) );
            
            getView().stop();
            itsSplashView.start();
            
            itsSplashView.setMessage( "Initializing modules..." );
            itsInitializer.run();
            itsSplashView.setMessage( "Initialization complete." );
            itsSplashView.stop(); 
            itsLogger.log( LoggingLevel.INFO,"Starting application." );
            itsActivator.run();
        }
        catch (AuthenticationFailureException e)
        {
            if ( e.hasInvalidField( "UserName" ) )
                getView().setInvalidUserName();
            else if ( e.hasInvalidField( "Password" ) )
                getView().setInvalidPassword();
            else
                getView().setLoginError( e.getMessage() );
        }
    }

    /************************************************************************
     *  
     *
     */
    protected void
    doCancel()
    {
        itsLogger.log( LoggingLevel.INFO,"Cancelling login and exiting." );
        System.exit(  0 );
    }
}

// ##########################################################################
