// ##########################################################################
// # File Name:	ApplicationBootstrapper.java
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

package strata1.injector.bootstrap;

import java.util.Collections;
import java.util.List;
import strata1.common.commandline.ICommandLineParser;
import strata1.common.logger.ILogger;
import strata1.common.task.ITaskFlowManager;
import strata1.injector.container.Binder;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ApplicationBootstrapper
    implements IApplicationBootstrapper
{
    private ICommandLineParser  itsCommandLineParser;
    private IContainer          itsContainer;
    private ILogger             itsLogger;
    private List<IModule>       itsModules;
    private IApplicationStarter itsApplicationStarter;
    
    /************************************************************************
     * Creates a new {@code ApplicationBootstrapper}. 
     *
     * @param start
     */
    public 
    ApplicationBootstrapper()
    {
        itsCommandLineParser  = null;
        itsContainer          = null;
        itsLogger             = null;
        itsModules            = null;
        itsApplicationStarter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineParser 
    getCommandLineParser()
    {
        return itsCommandLineParser;
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
    public IApplicationStarter 
    getApplicationStarter()
    {
        return itsApplicationStarter;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IApplicationFactory factory,String[] arguments)
    {
        try
        {
            createCommandLineParser(factory);
            
            if ( mustParseCommandLine(arguments) )
                parseCommandLine(arguments);
            
            createContainer(factory);
            createLogger(factory);
            createModules(factory);
            createApplicationStarter(factory);
            initializeModules();
            startApplication();
        }
        catch (Throwable t)
        {
            if ( getLogger() != null )
                getLogger().logError( t.getMessage() );
            
            throw new IllegalStateException( t );
        }
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void
    createCommandLineParser(IApplicationFactory factory)
    {
        itsCommandLineParser = factory.createCommandLineParser();
    }
    
    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void 
    createContainer(IApplicationFactory factory)
    {
        itsContainer = factory.createContainer();
        
        if ( getContainer() == null )
            throw new IllegalStateException("container is null");
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void 
    createLogger(IApplicationFactory factory)
    {
        itsLogger = factory.createLogger();
        
        if ( getLogger() == null )
            throw new IllegalStateException("logger is null");
        
        getLogger().logInfo( "Registering logger with container" );
        getContainer()
            .insertBinding(
                Binder
                    .bindType( ILogger.class )
                    .toInstance( itsLogger ) );
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void 
    createModules(IApplicationFactory factory)
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
     * @param factory
     */
    protected void
    createApplicationStarter(IApplicationFactory factory)
    {
        getLogger().logInfo( "Creating application starter." );
        itsApplicationStarter = factory.createApplicationStarter();
        
        if ( getApplicationStarter() == null )
            throw new IllegalStateException( "application starter is null." );
        
    }
    
    /************************************************************************
     *  
     *
     * @param arguments
     * @return
     */
    protected boolean
    mustParseCommandLine(String[] arguments)
    {
        return
            (arguments.length > 0) &&
            (getCommandLineParser() != null);
    }
    
    /************************************************************************
     *  
     *
     * @param arguments
     * @throws Throwable
     */
    protected void
    parseCommandLine(String[] arguments) 
        throws Throwable
    {
        getCommandLineParser().parse( arguments );
    }
    
    /************************************************************************
     *  
     *
     */
    protected void 
    initializeModules()
    {
        getLogger().logInfo( "Initializing server modules." );
        
        for (IModule module : getModules())
        {
            getLogger()
                .logInfo("Initalizaing module: " + module.getName() + ".");
            module.initialize( getContainer() );
        }
    }

    /************************************************************************
     *  
     *
     */
    protected void 
    startApplication()
    {
        getApplicationStarter().startApplication( getContainer() );
    }


}

// ##########################################################################
