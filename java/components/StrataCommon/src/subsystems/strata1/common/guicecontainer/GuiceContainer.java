// ##########################################################################
// # File Name:	GuiceContainer.java
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

package strata1.common.guicecontainer;

import strata1.common.container.ILinkedBindingBuilder;
import strata1.common.container.IConstructorInjector;
import strata1.common.container.IContainer;
import strata1.common.container.IPropertyInjector;
import strata1.common.container.ITypeDefinition;
import strata1.common.container.TypeLiteral;
import com.google.inject.Binder;
import com.google.inject.Injector;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceContainer
    implements IContainer
{
    private Binder   itsBinder;
    private Injector itsInjector;
    
    /************************************************************************
     * Creates a new {@code GuiceContainer}. 
     *
     */
    public 
    GuiceContainer()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> ILinkedBindingBuilder 
    bindType(Class<T> type)
    {
        return new GuiceBindingBuilder( itsBinder.bind( type ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>ILinkedBindingBuilder 
    bindType(TypeLiteral<T> type)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerType(ITypeDefinition definition)
    {
        // TODO Auto-generated method stub

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>void 
    registerInstance(T instance)
    {
        // TODO Auto-generated method stub

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>void 
    registerInstance(String name,T instance)
    {
        // TODO Auto-generated method stub

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T 
    resolveType(Class<T> type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T 
    resolveType(Class<T> type,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T 
    resolveInstance(Class<T> type)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T 
    resolveInstance(Class<T> type,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(ITypeDefinition definition)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(Class<?> type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasType(Class<?> type,String name)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasInstance(Class<?> type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasInstance(Class<?> type,String name)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ITypeDefinition 
    createTypeDefinition()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConstructorInjector 
    createConstructorInjector()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPropertyInjector 
    createPropertyInjector()
    {
        // TODO Auto-generated method stub
        return null;
    }

}

// ##########################################################################
