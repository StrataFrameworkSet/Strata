// ##########################################################################
// # File Name:	Container.java
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
import java.util.HashMap;
import java.util.Map;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Container
    implements IContainer
{
    private Map<IBindingIdentifier<?>,Provider<?>> itsBindings;
    
    /************************************************************************
     * Creates a new {@code Container}. 
     *
     */
    public 
    Container()
    {
        itsBindings = new HashMap<IBindingIdentifier<?>,Provider<?>>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBindingBuilder<T> builder)
    {
         return insertBinding( builder.getBinding() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBinding<T> binding)
    {
        BindingVisitor<T> visitor = new BindingVisitor<T>(this);
        
        if ( itsBindings.containsKey( binding.getIdentifier() ) )
            throw new IllegalArgumentException();

        binding.accept( visitor );     
        itsBindings.put( visitor.getIdentifier(),visitor.getProvider() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        IBindingIdentifier<T> id = new TypeBindingIdentifier<T>(type);
        Provider<?>           provider = null;
        
        if ( !hasBinding( id ) )
            return null;
        
        provider = itsBindings.get( id );
        
        if ( provider == null )
            throw new IllegalStateException("provider is null.");
        
        return type.cast( provider.get() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String key)
    {
        IBindingIdentifier<T> id = 
            new TypeAndNameBindingIdentifier<T>(type,key);
        Provider<?>           provider = null;
        
        if ( !hasBinding( id ) )
            return null;
        
        provider = itsBindings.get( id );
        
        if ( provider == null )
            throw new IllegalStateException("provider is null.");
        
        return type.cast( provider.get() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        IBindingIdentifier<T> id = 
            new TypeAndAnnotationBindingIdentifier<T>(type,key);
        Provider<?>           provider = null;
        
        if ( !hasBinding( id ) )
            return null;
        
        provider = itsBindings.get( id );
        
        if ( provider == null )
            throw new IllegalStateException("provider is null.");
        
        return type.cast( provider.get() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(IBindingIdentifier<T> id)
    {
        Provider<?> provider = null;
        
        if ( !hasBinding( id ) )
            return null;
        
        provider = itsBindings.get( id );
        
        if ( provider == null )
            throw new IllegalStateException("provider is null.");
        
        return id.getType().cast( provider.get() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        return hasBinding( new TypeBindingIdentifier<T>(type) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        return hasBinding(new TypeAndNameBindingIdentifier<T>(type,key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        return 
            hasBinding(
                new TypeAndAnnotationBindingIdentifier<T>(type,key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(IBindingIdentifier<T> id)
    {
        return itsBindings.containsKey( id );
    }

}

// ##########################################################################
