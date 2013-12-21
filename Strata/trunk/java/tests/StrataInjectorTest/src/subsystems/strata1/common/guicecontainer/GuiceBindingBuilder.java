// ##########################################################################
// # File Name:	GuiceBindingBuilder.java
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

import strata1.common.container.AnnotationLiteral;
import strata1.common.container.ILinkedBindingBuilder;
import strata1.common.container.LifetimeKind;
import strata1.common.container.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceBindingBuilder<T>
    implements ILinkedBindingBuilder<T>
{
    private AnnotatedBindingBuilder<T> itsImp;
    
    /************************************************************************
     * Creates a new {@code GuiceBindingBuilder}. 
     *
     * @param bind
     */
    public 
    GuiceBindingBuilder(AnnotatedBindingBuilder<T> imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILinkedBindingBuilder<T>
    toType(Class<? extends T> type)
    {
        itsImp.to(  type );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public ILinkedBindingBuilder<T>
    toType(TypeLiteral<? extends T> type)
    {
        itsImp.to( 
            (com.google.inject.TypeLiteral<? extends T>)
                com.google.inject.TypeLiteral.get(type.getType()) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public ILinkedBindingBuilder<T> 
    toProvider(Provider<T> provider)
    {
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILinkedBindingBuilder<T> toInstance(T instance)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILinkedBindingBuilder<T>
    withKey(String key)
    {
        itsImp.annotatedWith( Names.named( key ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> ILinkedBindingBuilder<T> 
    withKey(A key)
    {
        itsImp.annotatedWith( key );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILinkedBindingBuilder<T> 
    withLifetime(LifetimeKind lifetime)
    {
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends Provider<? extends T>>ILinkedBindingBuilder<T> toProvider(
        Class<P> provider)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends Provider<? extends T>>ILinkedBindingBuilder<T> toProvider(
        TypeLiteral<? extends P> provider)
    {
        // TODO Auto-generated method stub
        return null;
    }

}

// ##########################################################################
