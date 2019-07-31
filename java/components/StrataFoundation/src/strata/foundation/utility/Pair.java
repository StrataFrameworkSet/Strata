// ##########################################################################
// # File Name:	Pair.java
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

package strata.foundation.utility;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Pair<F,S>
{
    private final F itsFirst;
    private final S itsSecond;
    
    /************************************************************************
     * Creates a new {@code Pair}. 
     *
     * @param first
     * @param second
     */
    public 
    Pair(final F first,final S second)
    {
        itsFirst = first;
        itsSecond = second;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
        if ( other instanceof Pair<?,?> )
        {
            Pair<?,?> pair = (Pair<?,?>)other;
            
            return
                itsFirst.equals( pair.getFirst() ) &&
                itsSecond.equals( pair.getSecond() );
        }
        
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
        return
            new HashCodeBuilder(43)
                .append( itsFirst.toString() )
                .append( itsSecond.toString() )
                .getHashCode();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsFirst.toString() + "," + itsSecond.toString();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public F
    getFirst()
    {
        return itsFirst;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public S
    getSecond()
    {
        return itsSecond;
    }

    /************************************************************************
     *  
     *
     * @param first
     * @param second
     * @return
     */
    public static <F,S> Pair<F,S>
    create(final F first,final S second)
    {
        return new Pair<F,S>( first,second );
    }
}

// ##########################################################################
