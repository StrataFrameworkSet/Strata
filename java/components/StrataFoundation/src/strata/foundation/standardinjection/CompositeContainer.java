// ##########################################################################
// # File Name:	CompositeContainer.java
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
import strata.foundation.injection.IContainer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CompositeContainer 
    implements IContainer
{
    private final List<IContainer> itsContainers;
    
    /************************************************************************
     * Creates a new CompositeContainer. 
     *
     */
    public 
    CompositeContainer(IContainer... containers)
    {
        itsContainers = 
            new ArrayList<IContainer>( Arrays.asList(containers) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type ) )
                return container.getInstance( type );
        
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,String key)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type,key ) )
                return container.getInstance( type,key );
        
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getInstance(Class<T> type,Class<? extends Annotation> key)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type,key ) )
                return container.getInstance( type,key );
        
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type ) )
                return true;

        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,String key)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type,key ) )
                return true;

        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasBinding(Class<T> type,Class<? extends Annotation> key)
    {
        for (IContainer container : itsContainers)
            if ( container.hasBinding( type,key ) )
                return true;

        return false;
    }

}

// ##########################################################################
