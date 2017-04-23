// ##########################################################################
// # File Name:	AbstractApplicationFactory.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.bootstrap;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;
import strata.foundation.commandline.ICommandLineParser;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IBinder;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IKeyBindingBuilder;
import strata.foundation.injection.IModule;
import strata.foundation.injection.ProviderBasedBinder;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.JavaLogEntryProcessor;
import strata.foundation.logger.Logger;
import strata.foundation.standardinjection.StandardContainer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractApplicationFactory
    implements IApplicationFactory
{

    /************************************************************************
     * Creates a new {@code AbstractApplicationFactory}. 
     *
     */
    protected 
    AbstractApplicationFactory() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineParser 
    createCommandLineParser()
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBinder<ILogger> 
    createLoggerBinder(IKeyBindingBuilder<ILogger> builder)
    {
        return 
            new ProviderBasedBinder<ILogger>(
                builder,
                new Provider<ILogger>() 
                {
                    @Override
                    public ILogger 
                    get()
                    {
                        return 
                            new Logger()
                                .attachProcessor( 
                                    new JavaLogEntryProcessor() );
                    }},
                AbstractModule.getDefaultScope() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer()
    {
        List<IModule> modules = new ArrayList<IModule>();
        
        modules.add( createBootstrapModule() );
        modules.addAll( createModules() );
        return new StandardContainer( modules );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected BootstrapModule
    createBootstrapModule()
    {
        return new BootstrapModule( this );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected abstract List<IModule>
    createModules();
}

// ##########################################################################
