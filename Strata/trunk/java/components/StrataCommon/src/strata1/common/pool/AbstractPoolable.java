// ##########################################################################
// # File Name:	AbstractPoolable.java
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
public abstract 
class AbstractPoolable
    implements IPoolable
{
    private final IPool<? super AbstractPoolable> itsPool;
    private boolean                               itsAvailability;
    
    /************************************************************************
     * Creates a new {@code AbstractPoolable}. 
     *
     */
    protected 
    AbstractPoolable(IPool<? super AbstractPoolable> pool)
    {
        itsPool         = pool;
        itsAvailability = true;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    recycle()
    {
        if ( !itsPool.containsInstance(this))
            throw
                new IllegalStateException(
                    "Instance is not contained in pool." );
        
        itsPool.recyleInstance( this );
    }

   /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPool<? super AbstractPoolable> 
    getPool()
    {
        return itsPool;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isAvailable()
    {
        return itsAvailability;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    void 
    markAvailable()
    {
        itsAvailability = false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    void 
    markUnavailable()
    {
        itsAvailability = true;
    }

}

// ##########################################################################
