// ##########################################################################
// # File Name:	IPool.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IPool<T extends IPoolable>
{
    /************************************************************************
     * Obtains an instance from the pool of available instances. 
     *
     * @return an available instance
     */
    public T
    obtainInstance();
    
    /************************************************************************
     * Recycles an instance by clearing the instance and putting it back 
     * into the pool of available instances. 
     *
     * @param instance  the instance being recycled
     */
    public void
    recyleInstance(T instance);
    
    /************************************************************************
     * Determines if the specified instance is contained within the pool. 
     *
     * @param  instance the instance under scrutiny
     * @return true     if instance is contained in the pool
     */
    public boolean
    containsInstance(T instance);
}

// ##########################################################################
