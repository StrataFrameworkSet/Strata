// ##########################################################################
// # File Name:	AbstractDispatcher.java
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

package strata.foundation.producerconsumer;

import strata.foundation.utility.IMultiMap;
import strata.foundation.utility.MultiMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractDispatcher<T>
    implements IDispatcher<T>
{
    private IMultiMap<ISelector<T>,IConsumer<T>> itsConsumers;

    /************************************************************************
     * Creates a new {@code AbstractDispatcher}. 
     *
     */
    public 
    AbstractDispatcher()
    {
        itsConsumers = new MultiMap<ISelector<T>,IConsumer<T>>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public AbstractDispatcher<T> 
    attachConsumer(IConsumer<T> consumer)
    {
        itsConsumers.put( consumer.getSelector(),consumer );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public AbstractDispatcher<T> 
    detachConsumer(IConsumer<T> consumer)
    {
        itsConsumers.remove( consumer.getSelector(),consumer );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(IConsumer<T> consumer)
    {
        return itsConsumers.containsValue(consumer.getSelector(),consumer);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IMultiMap<ISelector<T>,IConsumer<T>>
    getConsumers()
    {
        return itsConsumers;
    }

}

// ##########################################################################
