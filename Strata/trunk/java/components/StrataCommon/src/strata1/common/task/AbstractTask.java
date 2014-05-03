// ##########################################################################
// # File Name:	AbstractTask.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.common.task;

import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractTask
    implements ITask
{
    private String             itsName;
    private Map<String,Object> itsProperties;
    
    /************************************************************************
     * Creates a new {@code AbstractTask}. 
     *
     */
    protected 
    AbstractTask(String name)
    {
        itsName       = name;
        itsProperties = new HashMap<String,Object>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> void 
    setProperty(String key,T property)
    {
        itsProperties.put( key,property );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getName()
    {
        return itsName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T 
    getProperty(Class<T> type,String key)
    {
        if ( hasProperty(type,key) )
            return type.cast(itsProperties.get( key ));
        
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> boolean 
    hasProperty(Class<T> type,String key)
    {
        return 
            itsProperties.containsKey( key ) &&
            itsProperties
                .get( key )
                .getClass()
                .isAssignableFrom( type );
    }
    
    
}

// ##########################################################################
