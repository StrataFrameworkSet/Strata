// ##########################################################################
// # File Name:	CountRequestConsumer.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommonTest Framework.
// #
// #   			The StrataCommonTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommonTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommonTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.producerconsumer;

import java.util.concurrent.atomic.AtomicInteger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CountRequestConsumer
    extends    AbstractConsumer<CountRequest>
    implements ICountRequestConsumer
{
    private final AtomicInteger itsCount;
    
    /************************************************************************
     * Creates a new {@code CountRequestConsumer}. 
     *
     */
    public 
    CountRequestConsumer(int typeId)
    {
        super( new CountRequestSelector(typeId));
        setExceptionHandler( new PrintStackTraceExceptionHandler() );
        itsCount = new AtomicInteger(0);
    }

     /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getCount()
    {
        return itsCount.get();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doConsume(CountRequest element)
    {
        itsCount.incrementAndGet();
    }

}

// ##########################################################################
