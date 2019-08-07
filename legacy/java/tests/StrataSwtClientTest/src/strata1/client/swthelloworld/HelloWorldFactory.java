// ##########################################################################
// # File Name:	HelloWorldFactory.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSwtClientTest Framework.
// #
// #   			The StrataSwtClientTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSwtClientTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSwtClientTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.swthelloworld;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;
import strata.foundation.bootstrap.AbstractApplicationFactory;
import strata.foundation.bootstrap.IStartStopController;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IBinder;
import strata.foundation.injection.IModule;
import strata.foundation.injection.ISourceBindingBuilder;
import strata.foundation.injection.ProviderBasedBinder;
import strata.foundation.injection.TargetBasedBinder;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.JavaLogEntryProcessor;
import strata.foundation.logger.Logger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HelloWorldFactory
    extends AbstractApplicationFactory
{

    /************************************************************************
     * Creates a new {@code HelloWorldFactory}. 
     *
     */
    public 
    HelloWorldFactory() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBinder<ILogger> 
    createLoggerBinder(ISourceBindingBuilder<ILogger> builder)
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
                                .attachProcessor( new JavaLogEntryProcessor() );
                    }
                    
                },
                AbstractModule.getDefaultScope());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBinder<IStartStopController> 
    createControllerBinder(ISourceBindingBuilder<IStartStopController> builder)
    {
        return 
            new TargetBasedBinder<IStartStopController>(
                builder,
                HelloWorldStartStopController.class,
                AbstractModule.getDefaultScope() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IModule> 
    createModules()
    {
        List<IModule> modules = new ArrayList<IModule>();
        
        modules.add( new SwtGreetingModule() );
        return modules;
    }

}

// ##########################################################################
