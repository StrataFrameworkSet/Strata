// ##########################################################################
// # File Name:	CountRequestSelector.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CountRequestSelector
    implements ICountRequestSelector
{
    private final int itsTypeId;
    
    /************************************************************************
     * Creates a new {@code CountRequestSelector}. 
     *
     */
    public 
    CountRequestSelector(int typeId)
    {
        itsTypeId = typeId;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof CountRequestSelector )
            return itsTypeId == ((CountRequestSelector)other).itsTypeId;
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        int hash = 7;
        
        hash = 31 * hash + itsTypeId;
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return "CountRequestSelector:"+itsTypeId;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    match(CountRequest element)
    {
        return element.getTypeId() == itsTypeId;
    }

}

// ##########################################################################
