// ##########################################################################
// # File Name:	ArrayPool.java
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

package strata1.common.pool;

import java.lang.reflect.Array;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ArrayPool<T extends AbstractPoolable>
    extends AbstractPool<T>
{
    private final T[] itsInstances;
    
    /************************************************************************
     * Creates a new {@code ArrayPool}. 
     *
     */
    @SuppressWarnings("unchecked")
    public 
    ArrayPool(Provider<T> provider,Class<T> type,int poolSize)
    {
        super( provider );
        itsInstances = (T[])Array.newInstance( type,poolSize );
        
        for (int i=0;i<itsInstances.length;i++)
            itsInstances[i] = provider.get();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected T 
    getAvailableInstance()
    {
        for (int i=0;i<itsInstances.length;i++)
            if ( itsInstances[i].isAvailable() )
                return itsInstances[i];
        
        throw new IllegalStateException("No available instances.");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected boolean 
    hasInstance(T instance)
    {
        for (int i=0;i<itsInstances.length;i++)
            if ( itsInstances[i] == instance )
                return true;
        
        return false;
    }


}

// ##########################################################################
