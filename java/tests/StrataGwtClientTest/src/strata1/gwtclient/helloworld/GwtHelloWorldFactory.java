// ##########################################################################
// # File Name:	GwtHelloWorldFactory.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtClientTest Framework.
// #
// #   			The StrataGwtClientTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtClientTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtClientTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.helloworld;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Provider;
import strata.foundation.bootstrap.AbstractApplicationFactory;
import strata.foundation.bootstrap.IStartStopController;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IBinder;
import strata.foundation.injection.IKeyBindingBuilder;
import strata.foundation.injection.IModule;
import strata.foundation.injection.ProviderBasedBinder;
import strata.foundation.injection.TargetBasedBinder;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.*;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtHelloWorldFactory
    extends AbstractApplicationFactory
{

    /************************************************************************
     * Creates a new {@code GwtHelloWorldFactory}. 
     *
     */
    public 
    GwtHelloWorldFactory() {}

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
    createControllerBinder(IKeyBindingBuilder<IStartStopController> builder)
    {
        return 
            new TargetBasedBinder<IStartStopController>(
                builder,
                GwtHelloWorldStartStopController.class,
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
        
        modules.add( new GwtGreetingModule() );
        return modules;
    }

}

// ##########################################################################
