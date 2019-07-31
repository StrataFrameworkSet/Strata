// ##########################################################################
// # File Name:	StandardContainer.java
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

package strata.foundation.standardinjection;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IBindingBuilder;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IModule;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StandardContainer 
    implements IContainer
{
    private IBindingMap             itsBindings;
    private final IScopeModifierMap itsModifiers;
    
    /************************************************************************
     * Creates a new StandardContainer. 
     *
     * @param modules
     */
    public 
    StandardContainer(List<IModule> modules)
    {
        List<StandardModuleAdapter> adapters = 
            new ArrayList<StandardModuleAdapter>();
        
        itsBindings  = new BindingMap();
        itsModifiers = new ScopeModifierMap();
        
        itsBindings
            .insertBinding( 
                new BindingBuilder<IContainer>(
                    IContainer.class,
                    itsModifiers)
                    .toInstance( this ) );
        
        for (IModule module : modules)
        {
            StandardModuleAdapter adapter = 
                new StandardModuleAdapter(itsModifiers);
            
            adapters.add( adapter );
            ((AbstractModule)module).setAdapter( adapter );
            
            adapter.initialize();
            
            for (IBindingBuilder<?> builder : adapter.getBuilders())
                itsBindings.insertBinding( builder.getBinding() );
        }
    }

    /************************************************************************
     * Creates a new StandardContainer. 
     *
     * @param modules
     */
    public
    StandardContainer(IModule... modules)
    {
        this( Arrays.asList( modules ) );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        return itsBindings.getInstance( type );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String key)
    {
        return itsBindings.getInstance( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        return itsBindings.getInstance( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        return itsBindings.hasBinding( type );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        return itsBindings.hasBinding( type,key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        return itsBindings.hasBinding( type,key );
    }

}

// ##########################################################################
