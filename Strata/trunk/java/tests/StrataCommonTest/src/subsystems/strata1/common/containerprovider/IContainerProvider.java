// ##########################################################################
// # File Name:	IContainerProvider.java
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

package strata1.common.containerprovider;


/**
 * Adapter interface for managing components in component registries 
 * or application contexts.
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IContainerProvider
{
	/************************************************************************
	 * Opens the {@code IContainerProvider} and makes it 
	 * ready to find components. 
	 */
	public void 
	open();
	
	/************************************************************************
	 * Refreshes the components referenced by the {@code IContainerProvider}. 
	 */
	public void 
	refresh();
	
	/************************************************************************
	 * Closes the {@code IContainerProvider} by releasing 
	 * its references to components. 
	 */
	public void 
	close();
	
	/************************************************************************
	 * Registers a definition of a component  
	 * with the {@code IContainerProvider}.
	 *
	 * @param definition   definition of the component being registered
	 */
	public void
	registerDefinition(IComponentDefinition definition);
	
    /************************************************************************
     * Registers an instance of a component with the 
     * {@code IContainerProvider} under the specified name.
     *
     * @param <T>           component class
     * @param instanceName  name of the instance being registered
     * @param instance      component instance being registered
     */
    public <T> void
    registerInstance(String instanceName,T instance);
    
	/************************************************************************
	 * Gets a component with the specified class and name. 
	 *
	 * @param  <T>			  component class
	 * @param  componentClass class of the instance being found
	 * @param  instanceName   name of the component being found
	 * @return component instance 
	 */
	public <T> T 
	getInstance(Class<T> componentClass,String instanceName);
	
    /************************************************************************
     * Queries the {@code IContainerProvider} if it has 
     * a component with the specified class and name. 
     *
     * @param  <T>            component class
     * @param  componentClass class of the instance being found
     * @param  instanceName   name of the component being found
     * @return true if provider has the specified instance 
     */
	public <T> boolean
	hasInstance(Class<T> componentClass,String instanceName);
}


// ##########################################################################
