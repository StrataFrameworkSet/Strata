// ##########################################################################
// # File Name:	CopyableObject.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.common.utility;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CopyableObject
    implements Copyable
{
    private String x;
    private int    y;
    private Double z;
    
    /************************************************************************
     * Creates a new {@code CopyableObject}. 
     *
     */
    public 
    CopyableObject(String a,int b,Double c)
    {
        super();
        x = a;
        y = b;
        z = c;
    }

    public 
    CopyableObject(CopyableObject other)
    {
        super();
        x = other.x;
        y = other.y;
        z = other.z;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CopyableObject 
    copy()
    {
        return new CopyableObject( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        return x.hashCode() + y + z.hashCode();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        return 
            (other instanceof CopyableObject) && 
            equals( (CopyableObject)other );
    }

    public boolean
    equals(CopyableObject other)
    {
        return x.equals( other.x ) && (y == other.y) && z.equals( other.z );
    }
}

// ##########################################################################
