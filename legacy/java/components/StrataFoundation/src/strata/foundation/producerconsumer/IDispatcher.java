// ##########################################################################
// # File Name:	IDispatcher.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IDispatcher<T>
{
    public IDispatcher<T>
    attachConsumer(IConsumer<T> consumer);
    
    public IDispatcher<T>
    detachConsumer(IConsumer<T> consumer);
    
    public void
    route(T payload);
    
    public void
    broadcast(T payload);
    
    public boolean
    hasConsumer(IConsumer<T> consumer);
    
    public void
    startDispatching();
    
    public void
    stopDispatching();
    
    public boolean
    isDispatching();
}

// ##########################################################################
