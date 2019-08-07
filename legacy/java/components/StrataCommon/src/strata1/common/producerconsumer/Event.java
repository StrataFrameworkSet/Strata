// ##########################################################################
// # File Name:	Event.java
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


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Event<T>
{
    private T            itsPayload;
    private DispatchKind itsKind;
    
    /************************************************************************
     * Creates a new {@code Event}. 
     *
     */
    public 
    Event()
    {
        itsPayload = null;
        itsKind    = DispatchKind.ROUTE;
    }

    /************************************************************************
     *  
     *
     * @param payload
     */
    public void
    setPayload(T payload)
    {
        itsPayload = payload;
    }
        
    /************************************************************************
     *  
     *
     * @param kind
     */
    public void
    setKind(DispatchKind kind)
    {
        itsKind = kind;
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
    
    /************************************************************************
     *  
     *
     * @return
     */
    public DispatchKind
    getKind()
    {
        return itsKind;
    }
}

// ##########################################################################
