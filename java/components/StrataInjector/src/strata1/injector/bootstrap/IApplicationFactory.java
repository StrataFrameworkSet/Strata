// ##########################################################################
// # File Name:	IApplicationFactory.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.bootstrap;

import java.util.List;
import strata1.common.commandline.ICommandLineParser;
import strata1.common.logger.ILogger;
import strata1.injector.container.IContainer;
import strata1.injector.container.IModule;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IApplicationFactory
{
    /************************************************************************
     *  
     *
     * @return
     */
    public ICommandLineParser
    createCommandLineParser();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IContainer
    createContainer();
    
    /************************************************************************
     *  
     *
     * @return
     */
    @Deprecated
    public ILogger
    createLogger();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ILoggerProvider
    createLoggerProvider();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public List<IModule>
    createModules();

    /************************************************************************
     *  
     *
     * @return
     */
    public IStartStopController
    createStartStopController();
}


// ##########################################################################
