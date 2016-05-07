// ##########################################################################
// # File Name:	Entry.java
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

package strata1.common.producerconsumer;

import java.util.concurrent.atomic.AtomicLong;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Entry<T>
    implements Comparable<Entry<T>>
{
    private static final AtomicLong theirSequence = new AtomicLong(0);
    private final long              itsSequence;
    private final int               itsPriority;
    private final T                 itsPayload;
    
    /************************************************************************
     * Creates a new {@code Entry}. 
     *
     */
    public 
    Entry(int priority,T payload)
    {
        itsSequence = theirSequence.getAndIncrement();
        itsPriority = priority;
        itsPayload  = payload;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    compareTo(Entry<T> other)
    {
        int result = itsPriority - other.itsPriority;
        
        if ( result != 0 )
            return result;
        
        return (int)(itsSequence - other.itsSequence);    
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public T
    getPayload()
    {
        return itsPayload;
    }
}

// ##########################################################################
