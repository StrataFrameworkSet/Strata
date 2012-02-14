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
    private String itsX;
    private int    itsY;
    private Double itsZ;
    
    /************************************************************************
     * Creates a new {@code CopyableObject}. 
     *
     * @param a
     * @param b
     * @param c
     */
    public 
    CopyableObject(String a,int b,Double c)
    {
        super();
        itsX = a;
        itsY = b;
        itsZ = c;
    }

    /************************************************************************
     * Creates a new {@code CopyableObject}. 
     *
     * @param other
     */
    public 
    CopyableObject(CopyableObject other)
    {
        super();
        itsX = other.itsX;
        itsY = other.itsY;
        itsZ = other.itsZ;
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
        return itsX.hashCode() + itsY + itsZ.hashCode();
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

    /************************************************************************
     *  
     *
     * @param other other {@code CopyableObject}
     * @return true if equals
     */
    public boolean
    equals(CopyableObject other)
    {
        return 
            itsX.equals( other.itsX ) && 
            (itsY == other.itsY) && 
            itsZ.equals( other.itsZ );
    }
}

// ##########################################################################
