// ##########################################################################
// # File Name:	GuiceSourceBindingBuilderAdapter.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundation Framework.
// #
// #   			The StrataFoundation Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundation Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundation
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.guiceinjection;

import java.lang.annotation.Annotation;
import javax.inject.Provider;
import com.google.inject.internal.BindingBuilder;
import com.google.inject.name.Names;
import strata.foundation.injection.IBinding;
import strata.foundation.injection.IBindingBuilder;
import strata.foundation.injection.IKeyBindingBuilder;
import strata.foundation.injection.IScopeBindingBuilder;
import strata.foundation.injection.ISourceBindingBuilder;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceBindingBuilderAdapter<T>
    implements ISourceBindingBuilder<T>
{
    private final BindingBuilder<T>   itsAdaptee;
    private final GuiceScopeConverter itsConverter;
    
    /************************************************************************
     * Creates a new GuiceSourceBindingBuilderAdapter. 
     *
     */
    public 
    GuiceBindingBuilderAdapter(BindingBuilder<T> adaptee)
    {
        if ( adaptee == null )
            throw new NullPointerException( "adaptee can not be null." );
        
        itsAdaptee   = adaptee;
        itsConverter = new GuiceScopeConverter();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toType(Class<? extends T> type)
    {
        return new GuiceBindingBuilderAdapter<T>(itsAdaptee.to(type));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toProvider(Provider<? extends T> provider)
    {
        return 
            new GuiceBindingBuilderAdapter<T>(
                itsAdaptee.toProvider( provider ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends Provider<? extends T>> IScopeBindingBuilder<T> 
    toProvider(Class<P> provider)
    {
        return 
            new GuiceBindingBuilderAdapter<T>(
                itsAdaptee.toProvider( provider ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toInstance(T instance)
    {
        itsAdaptee.toInstance( instance );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IKeyBindingBuilder<T> 
    withScope(Class<? extends Annotation> scope)
    {
        itsAdaptee.in( itsConverter.convert( scope ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBinding<T> 
    getBinding()
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingBuilder<T> 
    withKey(String key)
    {
        return 
            new GuiceBindingBuilderAdapter<T>(
                itsAdaptee.annotatedWith( Names.named( key ) ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> IBindingBuilder<T> 
    withKey(Class<A> key)
    {
        return 
            new GuiceBindingBuilderAdapter<T>(
                itsAdaptee.annotatedWith( key ));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> IBindingBuilder<T> 
    withKey(A key)
    {
        return 
            new GuiceBindingBuilderAdapter<T>(
                itsAdaptee.annotatedWith( key ));
    }

}

// ##########################################################################
