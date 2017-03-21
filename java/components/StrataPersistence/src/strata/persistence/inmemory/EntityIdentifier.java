// ##########################################################################
// # File Name:	EntityIdentifier.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.inmemory;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class EntityIdentifier
{
    private Class<?> itsType;
    private Object   itsKey;
    
    /************************************************************************
     * Creates a new {@code EntityIdentifier}. 
     *
     * @param type
     * @param key
     */
    public 
    EntityIdentifier(Class<?> type,Object key)
    {
        itsType = type;
        itsKey  = key;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object obj)
    {
        if ( obj instanceof EntityIdentifier )
        {
            EntityIdentifier other = (EntityIdentifier)obj;
            
            return
                itsType.equals( other.itsType ) &&
                itsKey.equals( other.itsKey );
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
        int hash = 7;
        
        hash = 31 * hash + itsType.hashCode();
        hash = 31 * hash + itsKey.hashCode();
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append( itsType ).append( "," );
        builder.append( itsKey );
        return builder.toString();
            
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public Class<?>
    getType()
    {
        return itsType;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Object
    getKey()
    {
        return itsKey;
    }
}

// ##########################################################################
