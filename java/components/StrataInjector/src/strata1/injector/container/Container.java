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

import strata1.injector.reflection.ITypeManager;
import strata1.common.synchronizer.ISynchronizer;
import strata1.common.synchronizer.NullSynchronizer;
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
    private final ITypeManager                     itsTypeManager;
    private final ISynchronizer                    itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code Container}. 
     *
     * @param manager
     */
    public 
    Container(ITypeManager manager)
    {
        this( manager,new NullSynchronizer() );
    }

    /************************************************************************
     * Creates a new {@code Container}. 
     *
     * @param manager
     * @param synchronizer
     */
    public 
    Container(ITypeManager manager,ISynchronizer synchronizer)
    {
        itsBindings     = new HashMap<IBindingIdentifier<?>,Provider<?>>();
        itsTypeManager  = manager;
        itsSynchronizer = synchronizer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBindingBuilder<T> builder)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            return insertBinding( builder.getBinding() );
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBinding<T> binding)
    {
        BindingVisitor<T> visitor = null;
        
        try
        {
            itsSynchronizer.lockForWriting();
            visitor = new BindingVisitor<T>(this);
            
            if ( itsBindings.containsKey( binding.getIdentifier() ) )
                throw new IllegalArgumentException();
    
            binding.accept( visitor );     
            itsBindings.put( visitor.getIdentifier(),visitor.getProvider() );
            return this;
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
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
        
        try
        {
            itsSynchronizer.lockForReading();
            id = new TypeBindingIdentifier<T>(type);
            
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
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
        
        try
        {
            itsSynchronizer.lockForReading();
            id = new TypeAndNameBindingIdentifier<T>(type,key);
           
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
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
        
        try
        {
            itsSynchronizer.lockForReading();
            id = new TypeAndAnnotationBindingIdentifier<T>(type,key);
            
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return type.cast( provider.get() );
            return (T)provider.get();
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
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
        
        try
        {
            itsSynchronizer.lockForReading();
            
            if ( !hasBinding( id ) )
                return null;
            
            provider = itsBindings.get( id );
            
            if ( provider == null )
                throw new IllegalStateException("provider is null.");
            
            //return id.getType().cast( provider.get() );
            return (T)provider.get();
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ITypeManager 
    getTypeManager()
    {
        return itsTypeManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        try
        {
            itsSynchronizer.lockForReading();
            return hasBinding( new TypeBindingIdentifier<T>(type) );
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        try
        {
            itsSynchronizer.lockForReading();
            return hasBinding(new TypeAndNameBindingIdentifier<T>(type,key));
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        try
        {
            itsSynchronizer.lockForReading();
            return 
                hasBinding(
                    new TypeAndAnnotationBindingIdentifier<T>(type,key));
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(IBindingIdentifier<T> id)
    {
        try
        {
            itsSynchronizer.lockForReading();
            return itsBindings.containsKey( id );
        }
        finally
        {
            itsSynchronizer.unlockFromReading();
        }
    }

}

// ##########################################################################
