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

package strata.foundation.bootstrap;

import strata.foundation.commandline.ICommandLineParser;
import strata.foundation.injection.IContainer;
import strata.foundation.logger.ILogger;

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
    public Definition<ILogger>
    createLoggerDefinition();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IStartStopController
    createStartStopController();

    /************************************************************************
     *  
     *
     * @return
     */
    public IContainer
    createContainer();
}


// ##########################################################################
