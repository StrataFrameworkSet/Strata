// ##########################################################################
// # File Name:	BindingVisitor.java
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.inject.Inject;
import javax.inject.Provider;
import strata.foundation.injection.IBinding;
import strata.foundation.injection.IBindingIdentifier;
import strata.foundation.injection.IBindingVisitor;
import strata.foundation.injection.IScopeModifier;
import strata.foundation.injection.InstanceProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BindingVisitor<T>
    implements IBindingVisitor<T>
{
    private IBindingMap           itsBindings;
    private IBindingIdentifier<T> itsIdentifier;
    private Provider<T>           itsProvider;
    
    /************************************************************************
     * Creates a new {@code BindingVisitor}. 
     *
     */
    public 
    BindingVisitor(IBindingMap bindings)
    {
        itsBindings   = bindings;
        itsIdentifier = null;
        itsProvider   = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitBinding(IBinding<T> binding)
    {
        binding.getIdentifier().accept( this );
        binding.getTarget().accept( this );
        binding.getScope().accept( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndNameBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndAnnotationBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(ClassBindingTarget<T> target)
    {
        Class<? extends T>       type        = target.getType();
        Constructor<? extends T> constructor = getInjectionConstructor(target.getType());
          
        itsProvider = 
            new ConstructorBasedProvider<T>(
                itsBindings,
                type,
                constructor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public void 
    visitTarget(ProviderBindingTarget<T> target)
    {
        Class<? extends Provider<? extends T>> providerType = 
            target.getProviderType();
        
        try
        {
            if ( target.hasProviderType() )
                itsProvider = getProviderFromType( providerType );       
            else if ( target.hasProviderInstance() )
                itsProvider = (Provider<T>)target.getProviderInstance();
            else
                throw new IllegalStateException("No provider."); 
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(InstanceBindingTarget<T> target)
    {
        itsProvider = new InstanceProvider<T>( target.getInstance() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitScope(IScopeModifier scope)
    {
        itsProvider = scope.getScopedProvider(itsProvider);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public IBindingIdentifier<T>
    getIdentifier()
    {
        if ( itsIdentifier == null )
            throw new IllegalStateException("identifier is null");
        
        return itsIdentifier;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Provider<T>
    getProvider()
    {
        if ( itsProvider == null )
            throw new IllegalStateException("provider is null");
        
        return itsProvider;
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    private Constructor<? extends T> 
    getInjectionConstructor(Class<? extends T> type)
    {
        
        if ( hasConstructor( type ) )
            return getConstructor( type );
        
        return getDefaultConstructor( type );        
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    @SuppressWarnings("unchecked")
    private Provider<T>
    getProviderFromType(Class<? extends Provider<? extends T>> type) 
        throws 
            InstantiationException, 
            IllegalAccessException, 
            IllegalArgumentException, 
            InvocationTargetException
    {
        Constructor<?> constructor = getDefaultProviderConstructor(type);
        
        return (Provider<T>)constructor.newInstance();
        
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    public Constructor<T> 
    getConstructor(Class<? extends T> type)
    {
        Constructor<?>[] constructors = type.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.isAnnotationPresent( Inject.class ))
                return (Constructor<T>)constructor;
        
        throw 
            new IllegalStateException(
                "No suitable constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    public Constructor<T> 
    getDefaultConstructor(Class<? extends T> type)
    {
        Constructor<?>[] constructors = type.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.getParameterTypes().length == 0 )
                return (Constructor<T>)constructor;
               
        throw 
            new IllegalStateException(
                "No suitable constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    public Constructor<Provider<? extends T>> 
    getDefaultProviderConstructor(Class<? extends Provider<? extends T>> type)
    {
        Constructor<?>[] constructors = type.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.getParameterTypes().length == 0 )
                return (Constructor<Provider<? extends T>>)constructor;
               
        throw 
            new IllegalStateException(
                "No suitable constructors for dependency injection." );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public boolean 
    hasConstructor(Class<? extends T> type)
    {
        Constructor<?>[] constructors = type.getConstructors();
        
        for (Constructor<?> constructor : constructors)
            if ( constructor.isAnnotationPresent( Inject.class ))
                return true;
        
        return false;
    }
    
}

// ##########################################################################
