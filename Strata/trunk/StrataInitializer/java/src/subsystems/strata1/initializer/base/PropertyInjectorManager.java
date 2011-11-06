// ##########################################################################
// # File Name:	DefaultPropertyInjectorManager.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.initializer.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PropertyInjectorManager
{
    private Map<String,PropertyInjector> itsInjectors;
    
    /************************************************************************
     * Creates a new {@code DefaultPropertyInjectorManager}. 
     *
     */
    public 
    PropertyInjectorManager()
    {
        itsInjectors = new HashMap<String,PropertyInjector>();
    }

    /************************************************************************
     *  
     *
     * @param injector
     */
    public void 
    insertInjector(PropertyInjector injector)
    {
        itsInjectors.put( injector.getPropertyName(),injector );
    }

    /************************************************************************
     *  
     *
     * @param injector
     */
    public void 
    removeInjector(PropertyInjector injector)
    {
        itsInjectors.remove( injector.getPropertyName() );
    }

    /************************************************************************
     *  
     *
     * @param propertyName
     * @return
     */
    public PropertyInjector 
    getInjector(String propertyName)
    {
        return itsInjectors.get( propertyName );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public List<PropertyInjector> 
    getInjectors()
    {
         return new ArrayList<PropertyInjector>(itsInjectors.values());
    }

    /************************************************************************
     *  
     *
     * @param propertyName
     * @return
     */
    public boolean 
    hasInjector(String propertyName)
    {
        return itsInjectors.containsKey( propertyName );
    }

}

// ##########################################################################
