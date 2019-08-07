// ##########################################################################
// # File Name:	GuiceContainer.java
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.name.Names;
import strata.foundation.injection.AbstractModule;
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
class GuiceContainer
    implements IContainer
{
    private Injector itsImp;
    
    /************************************************************************
     * Creates a new GuiceContainer. 
     *
     * @param modules1 strata modules
     * @param modules2 guice modules
     */
    public 
    GuiceContainer(List<IModule> modules1,List<Module> modules2)
    {
        ArrayList<Module> guiceModules = new ArrayList<Module>();
        
        guiceModules.add( new GuiceSetupModule( this ) );
        
        if ( modules1 != null )
        {
            for (IModule module : modules1)
            {
                GuiceModuleAdapter adapter = new GuiceModuleAdapter();
                
                ((AbstractModule)module).setAdapter( adapter );
                guiceModules.add( adapter );
            }
        }
        
        if ( modules2 != null )
            guiceModules.addAll( modules2 );
        
        itsImp = Guice.createInjector( guiceModules );
    }
    
    /************************************************************************
     * Creates a new GuiceContainer. 
     *
     * @param modules strata modules
     */
    public 
    GuiceContainer(IModule... modules)
    {
        this( Arrays.asList( modules ),null );
    }
    
    /************************************************************************
     * Creates a new GuiceContainer. 
     *
     * @param modules guice modules
     */
    public 
    GuiceContainer(Module... modules)
    {
        this( null,Arrays.asList( modules ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        return itsImp.getInstance( type );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String key)
    {
        return itsImp.getInstance( Key.get( type,Names.named( key ) ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        return itsImp.getInstance( Key.get( type,key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        return itsImp.getBinding( type ) != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        return 
            itsImp.getBinding(Key.get(type,Names.named(key))) != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        return itsImp.getBinding(Key.get(type,key)) != null;
    }

}

// ##########################################################################
