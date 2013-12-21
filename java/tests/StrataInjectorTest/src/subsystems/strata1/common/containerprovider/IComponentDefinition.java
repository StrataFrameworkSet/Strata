// ##########################################################################
// # File Name:	IComponentDefinition.java
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
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.containerprovider;



/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IComponentDefinition
{
    /************************************************************************
     *  
     *
     * @param componentName
     */
    public void
    setComponentName(String componentName);
    
    /************************************************************************
     *  
     *
     * @param componentType
     */
    public void
    setComponentType(Class<?> componentType);

    /************************************************************************
     *  
     *
     * @param interfaceType
     * @param implementationType
     */
    public void
    setComponentType(Class<?> interfaceType,Class<?> implementationType);
   
    /************************************************************************
     *  
     *
     * @param injector
     */
    public void
    setConstructorInjector(IConstructorInjector injector);
    
    /************************************************************************
     *  
     *
     * @param scope
     */
    public void
    setScope(ComponentScope scope);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getComponentName();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Class<?> 
    getComponentType();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IConstructorInjector
    getConstructorInjector();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public PropertyInjectorManager
    getPropertyInjectorManager();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ComponentScope
    getScope();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasConstructorInjector();
}

// ##########################################################################
