// ##########################################################################
// # File Name:	ClientContainer.java
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

package strata1.initializer.client;

import strata1.initializer.combined.CombinedContainer;
import strata1.interactor.model.Model;
import strata1.interactor.view.View;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ClientContainer
    extends CombinedContainer
{
    public <V extends View> V
    getView(Class<V> viewType,String name);
    
    public <V> V
    getViewModel(Class<V> viewModelType,String name);
    
    public <C> C
    getController(Class<C> controllerType,String name);
    
    public <M> M
    getModel(Class<M> modelType,String name);  
}


// ##########################################################################
