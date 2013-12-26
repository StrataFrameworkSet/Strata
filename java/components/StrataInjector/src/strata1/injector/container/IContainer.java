// ##########################################################################
// # File Name:	IContainer.java
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

import java.lang.annotation.Annotation;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IContainer
{
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> ILinkedBindingBuilder<T>
    bindType(Class<T> type);
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> ILinkedBindingBuilder<T>
    bindType(TypeLiteral<T> type);
    
    public <T> void
    bindDecorator(IBindingMatcher matcher,Class<T> decoratorType);
    
    /************************************************************************
     *  
     *
     * @param type
     */
    public void
    registerType(ITypeDefinition definition);
    
    /************************************************************************
     *  
     *
     * @param instance
     */
    public <T> void
    registerInstance(T instance);
    
    /************************************************************************
     *  
     *
     * @param name
     * @param instance
     */
    public <T> void
    registerInstance(String name,T instance);
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> T
    resolveType(Class<T> type);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param name
     * @return
     */
    public <T> T
    resolveType(Class<T> type,String key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    public <T> T
    resolveType(Class<T> type,Class<? extends Annotation> key);
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public <T> T
    resolveInstance(Class<T> type);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param name
     * @return
     */
    public <T> T
    resolveInstance(Class<T> type,String name);
    
    /************************************************************************
     *  
     *
     * @param definition
     * @return
     */
    public boolean
    hasType(ITypeDefinition definition);
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public boolean
    hasType(Class<?> type);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param name
     * @return
     */
    public boolean
    hasType(Class<?> type,String name);
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    public boolean
    hasInstance(Class<?> type);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param name
     * @return
     */
    public boolean
    hasInstance(Class<?> type,String name);
    
    /************************************************************************
     *  
     *
     * @return
     */
    public ITypeDefinition
    createTypeDefinition();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IConstructorInjector
    createConstructorInjector();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IPropertyInjector
    createPropertyInjector();
}

// ##########################################################################
