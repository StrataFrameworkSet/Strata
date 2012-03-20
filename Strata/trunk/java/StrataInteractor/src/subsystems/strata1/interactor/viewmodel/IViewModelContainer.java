// ##########################################################################
// # File Name:	IViewModelContainer.java
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

package strata1.interactor.viewmodel;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IViewModelContainer
{
    public <V extends IViewModel> void
    registerViewModel(V viewmodel);
    
    public <V extends IViewModel> void
    registerViewModel(String viewmodelName,V viewmodel);
    
    public <V extends IViewModel> V
    getViewModel(Class<V> viewmodelType);
    
    public <V extends IViewModel> V
    getViewModel(Class<V> viewmodelType,String viewmodelName);
    
    public <V extends IViewModel> boolean
    hasViewModel(Class<V> viewmodelType);
    
    public <V extends IViewModel> boolean
    hasViewModel(Class<V> viewmodelType,String viewmodelName);    
}

// ##########################################################################
