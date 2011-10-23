// ##########################################################################
// # File Name:	ComponentDefinition.java
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

package strata1.common.container;


/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ComponentDefinition
{
    public void
    setComponentName(String componentName);
    
    public void
    setComponentType(Class<?> componentType);

    public void
    setComponentType(Class<?> interfaceType,Class<?> implementationType);
   
    public void
    setConstructorInjector(ConstructorInjector injector);
    
    public void
    setScope(ComponentScope scope);
    
    public String
    getComponentName();
    
    public Class<?> 
    getComponentType();
    
    public ConstructorInjector
    getConstructorInjector();
    
    public PropertyInjectorManager
    getPropertyInjectorManager();
    
    public ComponentScope
    getScope();
    
    public boolean
    hasConstructorInjector();
}

// ##########################################################################
