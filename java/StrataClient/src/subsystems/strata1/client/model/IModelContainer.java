// ##########################################################################
// # File Name:	IModelContainer.java
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

package strata1.client.model;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IModelContainer
{
    public <M extends IModel> void
    registerModel(M model);
    
    public <M extends IModel> void
    registerModel(String modelName,M model);
    
    public <M extends IModel> M
    getModel(Class<M> modelType);
    
    public <M extends IModel> M
    getModel(Class<M> modelType,String modelName);
    
    public <M extends IModel> boolean
    hasModel(Class<M> modelType);
    
    public <M extends IModel> boolean
    hasModel(Class<M> modelType,String modelName);    
}

// ##########################################################################
