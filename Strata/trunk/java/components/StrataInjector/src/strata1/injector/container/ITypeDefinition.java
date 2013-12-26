// ##########################################################################
// # File Name:	ITypeDefinition.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.container;

import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ITypeDefinition
{
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public ITypeDefinition
    setType(Class<?> type);
    
    /************************************************************************
     *  
     *
     * @param interfaceType
     * @param implementationType
     * @return
     */
    public ITypeDefinition
    setType(Class<?> interfaceType,Class<?> implementationType);
    
    /************************************************************************
     *  
     *
     * @param name
     * @return
     */
    public ITypeDefinition
    setName(String name);

    /************************************************************************
     *  
     *
     * @param lifetime
     * @return
     */
    public ITypeDefinition
    setLifetime(LifetimeKind lifetime);
    
    /************************************************************************
     *  
     *
     * @param injector
     * @return
     */
    public ITypeDefinition
    setConstructorInjector(IConstructorInjector injector);
    
    /************************************************************************
     *  
     *
     * @param injector
     * @return
     */
    public ITypeDefinition
    insertPropertyInjector(IPropertyInjector injector);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ITypeDefinition
    removeConstructorInjector();
    
    /************************************************************************
     *  
     *
     * @param propertyName
     * @return
     */
    public ITypeDefinition
    removePropertyInjector(String propertyName);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Class<?>
    getInterfaceType();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Class<?>
    getImplementationType();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getName();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public LifetimeKind
    getLifetime();
    
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
    public List<IPropertyInjector>
    getPropertyInjectors();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasName();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasConstructorInjector();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasPropertyInjectors();
}

// ##########################################################################
