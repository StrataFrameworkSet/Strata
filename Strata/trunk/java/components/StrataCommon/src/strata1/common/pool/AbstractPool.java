// ##########################################################################
// # File Name:	AbstractPool.java
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

import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractPool<T extends AbstractPoolable>
    implements IPool<T>
{
    private Provider<T> itsProvider;
    
    /************************************************************************
     * Creates a new {@code AbstractPool}. 
     *
     */
    protected 
    AbstractPool(Provider<T> provider) 
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public T 
    obtainInstance()
    {
        T instance = getAvailableInstance();
        
        instance.markAvailable();
        return instance;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    recyleInstance(T instance)
    {
        instance.markUnavailable();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    containsInstance(T instance)
    {
        return hasInstance( instance );
    }
    
    protected abstract T
    getAvailableInstance();
    
    protected abstract boolean
    hasInstance(T instance);
}

// ##########################################################################
