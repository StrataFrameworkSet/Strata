// ##########################################################################
// # File Name:	ServerBootstrapper.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataServer Framework.
// #
// #   			The StrataServer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataServer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataServer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.server.bootstrap;

import java.util.Collections;
import java.util.List;
import strata1.common.logger.ILogger;
import strata1.common.logger.LoggingLevel;
import strata1.common.producerconsumer.ITaskProducerConsumerManager;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ServerBootstrapper
    implements IServerBootstrapper
{
    private IServerContainer             itsContainer;
    private ILogger                      itsLogger;
    private ITaskProducerConsumerManager itsTaskManager;
    private List<IServerModule>          itsModules;
    private boolean                      itsStartIndicator;
    
    /************************************************************************
     * Creates a new {@code ServerBootstrapper}. 
     *
     */
    public
    ServerBootstrapper()
    {
        this( false );
    }
    
    /************************************************************************
     * Creates a new {@code ServerBootstrapper}. 
     *
     * @param start
     */
    public 
    ServerBootstrapper(boolean start)
    {
        itsContainer      = null;
        itsLogger         = null;
        itsTaskManager    = null;
        itsModules        = null;
        itsStartIndicator = start;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IServerContainer 
    getContainer()
    {
        return itsContainer;
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
    public ITaskProducerConsumerManager 
    getTaskManager()
    {
        return itsTaskManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IServerModule> 
    getModules()
    {
        return Collections.unmodifiableList( itsModules );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IServerFactory factory)
    {
        try
        {
            CreateContainer(factory);
            CreateLogger(factory);
            CreateTaskManager(factory);
            CreateModules(factory);
            InitializeModules();
            
            if ( MustStartServer() )
                StartServer();
        }
        catch (Exception e)
        {
            if ( getLogger() != null )
                getLogger().logError( e.getMessage() );
        }
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    private void 
    CreateContainer(IServerFactory factory)
    {
        itsContainer = factory.createServerContainer();
        
        if ( getContainer() == null )
            throw new IllegalStateException("container is null");
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    private void 
    CreateLogger(IServerFactory factory)
    {
        itsLogger = factory.createLogger();
        
        if ( getLogger() == null )
            throw new IllegalStateException("logger is null");
        
        getLogger().logInfo( "Registering logger with container" );
        //getContainer().registerLogger(itsLogger);
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    private void 
    CreateTaskManager(IServerFactory factory)
    {
        getLogger().logInfo( "Creating task manager." );
        itsTaskManager = factory.createTaskProducerConsumerManager();
        
        if ( getTaskManager() == null )
            throw new IllegalStateException( "task manager is null" );
        
        getLogger().logInfo( "Creating task consumer." );
        getTaskManager().attachConsumer( factory.createTaskConsumer() );
        
        getLogger().logInfo( "Registering task manager with container." );
        //getContainer().registerTaskProducerConsumerManager(getTaskManager());
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    private void 
    CreateModules(IServerFactory factory)
    {
        getLogger().logInfo( "Creating server modules." );
        itsModules = factory.createModules();
        
        if ( getModules() == null )
            throw new IllegalStateException( "modules is null." );
        
        if ( getModules().isEmpty() )
            throw new IllegalStateException( "no modules were created." );
    }

    /************************************************************************
     *  
     *
     */
    private void 
    InitializeModules()
    {
        getLogger().logInfo( "Initializing server modules." );
        
        for (IServerModule module : getModules())
        {
            getLogger()
                .logInfo("Initalizaing module: " + module.getName() + ".");
            module.initialize( this );
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    MustStartServer()
    {
        return itsStartIndicator;
    }

    /************************************************************************
     *  
     *
     */
    private void 
    StartServer()
    {
        getTaskManager().startUp();
    }


}

// ##########################################################################
