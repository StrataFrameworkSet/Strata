// ##########################################################################
// # File Name:	IBootstrapper.java
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

import java.util.List;
import strata.foundation.commandline.ICommandLineParser;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IModule;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IBootstrapper
{
    public ICommandLineParser
    getCommandLineParser();
    
    public List<IModule>
    getModules();

    public IContainer
    getContainer();

    public IStartStopController
    getStartStopController();
    
    public void
    run(IApplicationFactory factory);
    
    public void
    run(IApplicationFactory factory,String[] arguments);
}


// ##########################################################################
