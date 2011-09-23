// ##########################################################################
// # File Name:	ComponentContainer.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.platform;

/**
 * Adapter interface for finding components in component registries 
 * or application contexts.
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ComponentManager
{
	/************************************************************************
	 * Opens the {@code ComponentManager} and makes it 
	 * ready to find components. 
	 */
	public void 
	open();
	
	/************************************************************************
	 * Refreshes the components referenced by the {@code ComponentManager}. 
	 */
	public void 
	refresh();
	
	/************************************************************************
	 * Closes the {@code ComponentManager} by releasing 
	 * its references to components. 
	 */
	public void 
	close();
	
	/************************************************************************
	 * Gets a component with the specificed class and name. 
	 *
	 * @param <T>				component class
	 * @param componentClass	class of the component being found
	 * @param componentName		name of the component being found
	 * @return
	 */
	public <T> T 
	getComponent(Class<T> componentClass,String componentName);
}


// ##########################################################################
