// ##########################################################################
// # File Name:	AbstractClientBootstrapper.java
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

import strata1.injector.container.Binder;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.commandline.ICommandLineProcessor;
import strata1.common.logger.ILogger;
import strata1.common.logger.LoggingLevel;
import strata1.client.region.IRegionManager;
import strata1.client.region.RegionInitializationException;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import java.util.Collections;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
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
public abstract
class AbstractClientBootstrapper
    implements IClientBootstrapper
{
    private ICommandLineProcessor itsProcessor;
    private ILogger               itsLogger;
    private List<IModule>         itsModules;
    private IContainer            itsContainer;
    private IRegionManager        itsRegionManager;
    private IDispatcher           itsDispatcher;
    private ILoginView            itsLoginView;
    private ISplashView           itsSplashView;
    private IClientAuthenticator  itsAuthenticator;
    private IStartUpController    itsController;
    
    /************************************************************************
     * Creates a new {@code AbstractClientBootstrapper}. 
     *
     */
    public 
    AbstractClientBootstrapper()
    {
        itsProcessor     = null;
        itsLogger        = null;
        itsModules       = null;
        itsContainer     = null;
        itsRegionManager = null;
        itsLoginView     = null;
        itsSplashView    = null;
        itsAuthenticator = null;
        itsController    = new StartUpController();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setCommandLineProcessor(ICommandLineProcessor processor)
    {
        itsProcessor = processor;
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
    setModules(List<IModule> modules)
    {
        itsModules = modules;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setContainer(IContainer container)
    {
        itsContainer = container;
        itsContainer
            .insertBinding(
                Binder
                    .bindType( ILogger.class )
                    .toInstance( getLogger() ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setRegionManager(IRegionManager manager)
    {
        itsRegionManager = manager;
        getContainer()
            .insertBinding(
                Binder
                    .bindType( IRegionManager.class )
                    .toInstance( itsRegionManager ) );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setDispatcher(IDispatcher dispatcher)
    {
        itsDispatcher = dispatcher;
        getContainer()
            .insertBinding(
                Binder
                    .bindType( IDispatcher.class )
                    .toInstance( itsDispatcher ) );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginView(ILoginView loginView)
    {
        itsLoginView = loginView;
        getContainer()
            .insertBinding(
                Binder
                    .bindType( ILoginView.class )
                    .toInstance( itsLoginView ) );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setSplashView(ISplashView splashView)
    {
        itsSplashView = splashView;
        getContainer()
            .insertBinding(
                Binder
                    .bindType( ISplashView.class )
                    .toInstance( itsSplashView ) );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setAuthenticator(IClientAuthenticator authenticator)
    {
        itsAuthenticator = authenticator;
        getContainer()
            .insertBinding(
                Binder
                    .bindType( IClientAuthenticator.class )
                    .toInstance( itsAuthenticator ) );        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineProcessor 
    getCommandLineProcessor()
    {
        return itsProcessor;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    getLogger()
    {
        return itsLogger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IModule> 
    getModules()
    {
        return Collections.unmodifiableList( itsModules );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    getContainer()
    {
        return itsContainer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRegionManager 
    getRegionManager()
    {
        return itsRegionManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    getDispatcher()
    {
        return itsDispatcher;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILoginView 
    getLoginView()
    {
        return itsLoginView;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISplashView 
    getSplashView()
    {
        return itsSplashView;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientAuthenticator 
    getAuthenticator()
    {
        return itsAuthenticator;
    }

    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStartUpController 
    getController()
    {
        return itsController;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IClientFactory factory,String[] arguments)
    {
        try
        {
            setLogger( factory.createLogger() );
            getLogger().log( LoggingLevel.INFO,"Creating container." );
            setContainer( factory.createContainer() );
            setCommandLineProcessor( factory.createCommandLineProcessor() );
            getLogger().log( 
                LoggingLevel.INFO,"Processing command line arguments." );
            processCommandLineArguments( arguments );
            
            getLogger().log( LoggingLevel.INFO,"Creating modules." );
            setModules( factory.createModules() );
            getLogger().log( LoggingLevel.INFO,"Creating region manager." );
            setRegionManager( factory.createRegionManager() );
            
            getLogger().log( LoggingLevel.INFO,"Configuring region manager." );
            configureRegionManager();

            getLogger().log( LoggingLevel.INFO,"Creating dispatcher." );
            setDispatcher( factory.createDispatcher() );
            getLogger().log( LoggingLevel.INFO,"Creating login view." );
            setLoginView( factory.createLoginView( getDispatcher() ) );
            getLogger().log( LoggingLevel.INFO,"Creating splash view." );
            setSplashView( factory.createSplashView( getDispatcher() ) );
            getLogger().log( LoggingLevel.INFO,"Creating authenticator." );
            setAuthenticator( factory.createAuthenticator() );
            getLogger().log( LoggingLevel.INFO,"Configuring controller." );
            configureController();
            getLogger().log( LoggingLevel.INFO,"Starting dispatcher." );
            startDispatcher(); 
        }
        catch (Exception e)
        {
            getLogger().log( LoggingLevel.ERROR,e.getMessage() );
        }
    }

    /************************************************************************
     *  
     *
     * @param arguments
     */
    protected void 
    processCommandLineArguments(String[] arguments)
    {        
        getCommandLineProcessor().process( arguments );
    }

    /************************************************************************
     *  
     *
     */
    protected abstract void
    configureRegionManager();
        
    /************************************************************************
     *  
     *
     */
    protected void 
    initializeModules()
    {
        getLogger().log( LoggingLevel.INFO,"Initializing modules." );
        getController().incrementInitializationProgress();
        
        for (IModule module:getModules())
        {
            module.initialize( getContainer() );
            getController().incrementInitializationProgress();
        }
    }

    /************************************************************************
     *  
     *
     * @throws RegionInitializationException
     */
    protected void
    initializeRegions() 
    {
        try
        {
            getLogger().log( LoggingLevel.INFO,"Initializing regions." );
            getRegionManager().initializeRegions();
            getController().incrementInitializationProgress();
        }
        catch(RegionInitializationException e)
        {
            getLogger().log( LoggingLevel.ERROR,e.getMessage() );
        }
    }
    
    /************************************************************************
     *  
     *
     */
    protected void
    configureController()
    {
        itsController.setLogger( getLogger() );
        itsController.setContainer( getContainer() );
        itsController.setLoginView( getLoginView() );
        itsController.setSplashView( getSplashView() );
        itsController.setAuthenticator( getAuthenticator() );
        itsController.setInitializationIncrements( getModules().size()+2 );        
    }
    
    /************************************************************************
     *  
     *
     */
    protected void 
    startDispatcher()
    {
        Runnable startUpTask = 
            new Runnable()
            {
                public void
                run()
                {   
                    startUp();
                }
            };

        getDispatcher().insertTask( startUpTask );
        getDispatcher().start();
    }
    
    /************************************************************************
     *  
     *
     */
    protected void
    startUp()
    {
        Runnable initializer = null;
        Runnable activator   = null;
        
        initializer = 
            new Runnable()
            {
                @Override
                public void 
                run()
                {
                    initializeModules();
                    initializeRegions();
                }
            };     
        
        activator = 
            new Runnable()
            {
                @Override
                public void 
                run()
                {
                    startApplication();
                }
            };     
                  
        getLogger().log( LoggingLevel.INFO,"Performing start-up." );        
        itsController.setInitializer( initializer );
        itsController.setActivator( activator );
        itsController.start();
    }

    /************************************************************************
     *  
     *
     */
    protected abstract void 
    startApplication();
}

// ##########################################################################
