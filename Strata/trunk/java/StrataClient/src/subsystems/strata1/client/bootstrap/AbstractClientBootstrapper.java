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

import strata1.interactor.region.IRegionManager;
import strata1.interactor.region.RegionInitializationException;
import strata1.interactor.shell.IDispatcher;
import strata1.interactor.view.ILoginView;
import strata1.interactor.view.ISplashView;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.logger.ILogger;
import strata1.common.logger.LoggingLevel;

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
    private ILogger              itsLogger;
    private IClientModuleManager itsModuleManager;
    private IClientContainer     itsContainer;
    private IRegionManager       itsRegionManager;
    private IDispatcher          itsDispatcher;
    private ILoginView           itsLoginView;
    private ISplashView          itsSplashView;
    private IClientAuthenticator itsAuthenticator;
    private IStartUpController   itsController;
    
    /************************************************************************
     * Creates a new {@code AbstractClientBootstrapper}. 
     *
     */
    public 
    AbstractClientBootstrapper()
    {
        itsLogger        = null;
        itsModuleManager = null;
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
    setLogger(ILogger logger)
    {
        itsLogger = logger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setModuleManager(IClientModuleManager modules)
    {
        itsModuleManager = modules;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setContainer(IClientContainer container)
    {
        itsContainer = container;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setRegionManager(IRegionManager manager)
    {
        itsRegionManager = manager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setDispatcher(IDispatcher dispatcher)
    {
        itsDispatcher = dispatcher;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginView(ILoginView loginView)
    {
        itsLoginView = loginView;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setSplashView(ISplashView splashView)
    {
        itsSplashView = splashView;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setAuthenticator(IClientAuthenticator authenticator)
    {
        itsAuthenticator = authenticator;
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
    public IClientModuleManager 
    getModuleManager()
    {
        return itsModuleManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
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
    run(IClientFactory factory)
    {
        try
        {
            setLogger( factory.createLogger() );
            getLogger().log( LoggingLevel.INFO,"Creating module manager." );
            setModuleManager( factory.createModuleManager() );
            getLogger().log( LoggingLevel.INFO,"Creating container." );
            setContainer( factory.createContainer() );
            getLogger().log( LoggingLevel.INFO,"Creating region manager." );
            setRegionManager( factory.createRegionManager() );
            
            getLogger().log( LoggingLevel.INFO,"Configuring modules." );
            configureModules();
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
            getLogger().log( LoggingLevel.SEVERE,e.getMessage() );
        }
    }

    /************************************************************************
     *  
     *
     */
    protected abstract void
    configureModules();
    
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
        getModuleManager().initialize( this );
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
            getLogger().log( LoggingLevel.SEVERE,e.getMessage() );
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
        itsController.setInitializationIncrements( 
            getModuleManager().getNumberOfModules()+2 );        
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
