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

package strata1.injector.container;

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
class BindingBuilder<T>
    implements IKeyBindingBuilder<T>
{
    // Binding Identifier
    private Class<T>                    itsInterfaceType;
    private String                      itsStringKey;
    private Class<? extends Annotation> itsAnnotationKey;
    
    // Binding Target
    private Class<? extends T>          itsImplementationType;
    private Class<? extends Provider<? extends T>> itsProviderType;
    private Provider<? extends T>        itsProviderInstance;
    private T                            itsInstance;
    
    private IBindingScope<T>             itsScope;
    
    /************************************************************************
     * Creates a new {@code BindingBuilder}. 
     *
     */
    public 
    BindingBuilder(Class<T> type)
    {
        itsInterfaceType = type;
        itsStringKey     = null;
        itsAnnotationKey = null;
        
        itsImplementationType = itsInterfaceType;
        itsProviderType       = null;
        itsProviderInstance   = null;
        itsInstance           = null;
        
        itsScope = new NullScope<T>();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISourceBindingBuilder<T> 
    withKey(String key)
    {
        itsStringKey = key;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> ISourceBindingBuilder<T> 
    withKey(Class<A> key)
    {
        itsAnnotationKey = key;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <A extends Annotation> ISourceBindingBuilder<T> 
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
    @SuppressWarnings("unchecked")
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
    public <P extends Provider<? extends T>>IScopeBindingBuilder<T> 
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
    public IBindingBuilder<T> 
    toInstance(T instance)
    {
        itsImplementationType = null;
        itsProviderType       = null;
        itsProviderInstance   = null;
        itsInstance           = instance;
        itsScope              = new SingletonScope<T>();
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingBuilder<T> 
    withScope(IBindingScope<T> scope)
    {
        itsScope = scope;
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
    private IBindingScope<T>
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
