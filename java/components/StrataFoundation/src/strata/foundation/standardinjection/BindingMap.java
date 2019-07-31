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

package strata.foundation.standardinjection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Provider;
import strata.foundation.injection.IBinding;
import strata.foundation.injection.IBindingBuilder;
import strata.foundation.injection.IBindingIdentifier;
import strata.foundation.utility.ISynchronizer;
import strata.foundation.utility.NullSynchronizer;
import strata.foundation.utility.ReadLock;
import strata.foundation.utility.WriteLock;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BindingMap
    implements IBindingMap
{
    private Map<IBindingIdentifier<?>,Provider<?>> itsBindings;
    private final ISynchronizer                    itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code Container}. 
     *
     * @param manager
     */
    public 
    BindingMap()
    {
        this( new NullSynchronizer() );
    }

    /************************************************************************
     * Creates a new {@code Container}. 
     *
     * @param manager
     * @param synchronizer
     */
    public 
    BindingMap(ISynchronizer synchronizer)
    {
        itsBindings     = new HashMap<IBindingIdentifier<?>,Provider<?>>();
        itsSynchronizer = synchronizer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IBindingMap 
    insertBinding(IBindingBuilder<T> builder)
    {
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            return insertBinding( builder.getBinding() );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IBindingMap 
    insertBinding(IBinding<T> binding)
    {
        BindingVisitor<T> visitor = null;
        
        try (WriteLock lock = new WriteLock(itsSynchronizer))
        {
            visitor = new BindingVisitor<T>(this);
            
            if ( itsBindings.containsKey( binding.getIdentifier() ) )
                throw new IllegalArgumentException();
    
            binding.accept( visitor );     
            itsBindings.put( visitor.getIdentifier(),visitor.getProvider() );
            return this;
        }        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        IBindingIdentifier<T> id = null;
        Provider<?>           provider = null;
        
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            id = new TypeBindingIdentifier<T>(type);
            
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T 
    getInstance(Class<T> type,String key)
    {
        IBindingIdentifier<T> id = null;
        Provider<?>           provider = null;
        
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            id = new TypeAndNameBindingIdentifier<T>(type,key);
           
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        IBindingIdentifier<T> id = null;   
        Provider<?>           provider = null;
        
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            id = new TypeAndAnnotationBindingIdentifier<T>(type,key);
            
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T 
    getInstance(IBindingIdentifier<T> id)
    {
        Provider<?> provider = null;
        
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return id.getType().cast( provider.get() );
            return (T)provider.get();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return hasBinding( new TypeBindingIdentifier<T>(type) );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return hasBinding(new TypeAndNameBindingIdentifier<T>(type,key));
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return 
                hasBinding(
                    new TypeAndAnnotationBindingIdentifier<T>(type,key));
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(IBindingIdentifier<T> id)
    {
        try (ReadLock lock = new ReadLock(itsSynchronizer))
        {
            return itsBindings.containsKey( id );
        }
    }

}

// ##########################################################################
