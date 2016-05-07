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

package strata1.injector.bootstrap;

import strata1.injector.container.Container;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;
import strata1.common.commandline.ICommandLineParser;
import strata1.common.logger.ILogger;
import strata1.common.logger.Logger;
import java.util.List;

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
    public IContainer 
    createContainer()
    {
        return new Container(null);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    createLogger()
    {
        return new Logger();
    }

}

// ##########################################################################
