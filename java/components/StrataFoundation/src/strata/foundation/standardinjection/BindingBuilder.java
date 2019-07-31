// ##########################################################################
// # File Name:	BindingBuilder.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.standardinjection;

import java.lang.annotation.Annotation;
import javax.inject.Provider;
import strata.foundation.injection.IBinding;
import strata.foundation.injection.IBindingBuilder;
import strata.foundation.injection.IBindingIdentifier;
import strata.foundation.injection.IBindingTarget;
import strata.foundation.injection.IKeyBindingBuilder;
import strata.foundation.injection.IScopeBindingBuilder;
import strata.foundation.injection.IScopeModifier;
import strata.foundation.injection.ISourceBindingBuilder;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BindingBuilder<T>
    implements ISourceBindingBuilder<T>
{
    // Binding Identifier
    private Class<T>                       itsInterfaceType;
    private String                         itsStringKey;
    private Class<? extends Annotation>    itsAnnotationKey;
    
    // Binding Target
    private Class<? extends T>             itsImplementationType;
    private Class<? extends Provider<? extends T>> 
                                           itsProviderType;
    private Provider<? extends T>          itsProviderInstance;
    private T                              itsInstance;
    
    private final IScopeModifierMap itsModifiers;
    private IScopeModifier          itsScope;
    
    /************************************************************************
     * Creates a new {@code BindingBuilder}. 
     *
     */
    public 
    BindingBuilder(
        Class<T>                       type,
        final IScopeModifierMap modifiers) 
    {
        itsInterfaceType = type;
        itsStringKey     = null;
        itsAnnotationKey = null;
        
        itsImplementationType = itsInterfaceType;
        itsProviderType       = null;
        itsProviderInstance   = null;
        itsInstance           = null;
        
        itsModifiers = modifiers;
        itsScope = new NullScopeModifier();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingBuilder<T> 
    withKey(String key)
    {
        itsStringKey = key;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> IBindingBuilder<T> 
    withKey(Class<A> key)
    {
        itsAnnotationKey = key;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> IBindingBuilder<T> 
    withKey(A key)
    {
        itsAnnotationKey = key.getClass();
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toType(Class<? extends T> type)
    {
        itsImplementationType = type;
        itsProviderType = null;
        itsInstance = null;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toProvider(Provider<? extends T> provider)
    {
        itsImplementationType = null;
        itsProviderType = null;
        itsProviderInstance = provider;
        itsInstance = null;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends Provider<? extends T>> IScopeBindingBuilder<T> 
    toProvider(Class<P> provider)
    {
        itsImplementationType = null;
        itsProviderType       = provider;
        itsProviderInstance   = null;
        itsInstance           = null;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IScopeBindingBuilder<T> 
    toInstance(T instance)
    {
        itsImplementationType = null;
        itsProviderType       = null;
        itsProviderInstance   = null;
        itsInstance           = instance;
        itsScope              = new SingletonScopeModifier();
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IKeyBindingBuilder<T> 
    withScope(Class<? extends Annotation> scope)
    {
        itsScope = itsModifiers.get( scope );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBinding<T> 
    getBinding()
    {
        return createBinding();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private IBinding<T>
    createBinding()
    {
        return 
            new Binding<T>(
                createBindingIdentifier(),
                createBindingTarget(),
                createBindingScope());
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    private IBindingIdentifier<T>
    createBindingIdentifier()
    {
        if ( itsStringKey != null )
            return
                new TypeAndNameBindingIdentifier<T>(
                    itsInterfaceType,
                    itsStringKey );
        
        if ( itsAnnotationKey != null )
            return 
                new TypeAndAnnotationBindingIdentifier<T>(
                    itsInterfaceType,
                    itsAnnotationKey );
        
        return new TypeBindingIdentifier<T>(itsInterfaceType);
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    private IBindingTarget<T>
    createBindingTarget()
    {
        if ( itsImplementationType != null  )
            return new ClassBindingTarget<T>(itsImplementationType);
        
        if ( itsProviderType != null )
            return new ProviderBindingTarget<T>(itsProviderType);
        
        if ( itsProviderInstance != null )
            return new ProviderBindingTarget<T>(itsProviderInstance);
        
        if ( itsInstance != null )
            return new InstanceBindingTarget<T>(itsInstance);
        
        throw 
            new IllegalStateException("No binding target specified" );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    private IScopeModifier
    createBindingScope()
    {
        try
        {
            return itsScope;         
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }
}

// ##########################################################################
