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

package strata1.injector.guicecontainer;

import strata1.injector.container.IBinding;
import strata1.injector.container.IBindingBuilder;
import strata1.injector.container.IBindingIdentifier;
import strata1.injector.container.IContainer;
import strata1.injector.container.TypeAndAnnotationBindingIdentifier;
import strata1.injector.container.TypeAndNameBindingIdentifier;
import strata1.injector.container.TypeBindingIdentifier;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import java.lang.annotation.Annotation;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    private Queue<GuiceBindingPair<?>> itsPending;
    private Injector                   itsInjector;
    
    /************************************************************************
     * Creates a new {@code GuiceContainer}. 
     *
     */
    public 
    GuiceContainer()
    {
        itsPending = new ConcurrentLinkedQueue<GuiceBindingPair<?>>();
        itsInjector = Guice.createInjector();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBindingBuilder<T> builder)
    {
        IBinding<T> binding = builder.getBinding();
        
        if ( hasBinding( binding.getIdentifier() ) )
            throw 
                new IllegalArgumentException(
                    "binding already exists in container.");
        
        itsPending.add( new GuiceBindingPair<T>(binding) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> IContainer 
    insertBinding(IBinding<T> binding)
    {
        if ( hasBinding( binding.getIdentifier() ) )
            throw 
                new IllegalArgumentException(
                    "binding already exists in container.");
        
        itsPending.add( new GuiceBindingPair<T>(binding) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        if ( !itsPending.isEmpty() )
            configureBindings();
        
        return itsInjector.getInstance( type );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String name)
    {
        return 
            itsInjector.getInstance( 
                com.google.inject.Key.get( 
                    type,
                    Names.named( name ) ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        return 
            itsInjector.getInstance( 
                com.google.inject.Key.get( 
                    type,
                    key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type,String key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>boolean hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(IBindingIdentifier<T> id)
    {
        return itsInjector.getExistingBinding( toKey(id) ) != null;
    }
    
    /************************************************************************
     *  
     *
     */
    private void
    configureBindings()
    {
        Module module = new GuiceAdapterModule(itsPending);
        
        if ( itsInjector == null )
            itsInjector = Guice.createInjector( module );
        else
            itsInjector = itsInjector.createChildInjector( module );
    }
    
    /************************************************************************
     *  
     *
     * @param id
     * @return
     */
    private <T> Key<T>
    toKey(IBindingIdentifier<T> id)
    {
        if ( id instanceof TypeBindingIdentifier<?>)
            return 
                Key.get( id.getType());
        
        if ( id instanceof TypeAndNameBindingIdentifier<?>)
            return 
                Key.get( 
                    id.getType(),
                    Names.named( 
                        ((TypeAndNameBindingIdentifier<T>)id).getKey()));
        
        if ( id instanceof TypeAndAnnotationBindingIdentifier<?>)
            return 
                Key.get( 
                    id.getType(),
                    ((TypeAndAnnotationBindingIdentifier<T>)id).getKey() );
        
        throw new IllegalArgumentException("id");
    }
    
    /************************************************************************
     *  
     *
     * @param literal
     * @return
     */
    public <T> com.google.inject.TypeLiteral<T>
    convertToGuiceTypeLiteral(com.google.inject.TypeLiteral<T> literal)
    {
        return new GuiceTypeLiteralFactory().create( literal );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T>T getInstance(IBindingIdentifier<T> id)
    {
        // TODO Auto-generated method stub
        return null;
    }

}

// ##########################################################################
