// ##########################################################################
// # File Name:	ILoginController.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.presentation.login;

import strata.foundation.injection.IContainer;
import strata.foundation.logger.ILogger;
import strata.presentation.command.ILoginProvider;
import strata.presentation.controller.IController;
import strata.presentation.model.INullModel;
import strata.presentation.view.ILoginView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ILoginController
    extends ILoginProvider,IController<ILoginProvider,ILoginView,INullModel>
{
    public void
    setLogger(ILogger logger);
    
    public void
    setContainer(IContainer container);
    
    public void
    setLoginView(ILoginView loginView);
    
    public void
    setMainController(IController<?,?,?> controller);
    
}

// ##########################################################################
