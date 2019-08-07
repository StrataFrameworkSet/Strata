// ##########################################################################
// # File Name:	Foo.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.messaging;

import java.io.Serializable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Foo
    implements Serializable
{
    private int    itsX;
    private double itsY;
    private String itsZ;
    
    /************************************************************************
     * Creates a new {@code Foo}. 
     *
     */
    public 
    Foo(int x,double y,String z)
    {
        itsX = x;
        itsY = y;
        itsZ = z;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof Foo )
            return
                itsX == ((Foo)other).itsX &&
                itsY == ((Foo)other).itsY &&
                itsZ.equals( ((Foo)other).itsZ );
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder
            .append( "x=" ).append( itsX )
            .append( ",y=" ).append( itsY )
            .append( ",z=" ).append( itsZ );
        
        return builder.toString();
    }

    
}

// ##########################################################################
