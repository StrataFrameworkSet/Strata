// ##########################################################################
// # File Name:	IControllerContainer.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.interactor.controller;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IControllerContainer
{
    public <C extends IController> void
    registerController(C controller);
    
    public <C extends IController> void
    registerController(String controllerName,C controller);
    
    public <C extends IController> C
    getController(Class<C> controllerType);
    
    public <C extends IController> C
    getController(Class<C> controllerType,String controllerName);
    
    public <C extends IController> boolean
    hasController(Class<C> controllerType);
    
    public <C extends IController> boolean
    hasController(Class<C> controllerType,String controllerName);    
}

// ##########################################################################
