// ##########################################################################
// # File Name:	PersonName.java
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

package strata.persistence.testdomain;

import strata.foundation.utility.ICopyable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonName
    implements ICopyable
{
    private String itsFirstName;
    private String itsMiddleName;
    private String itsLastName;
    
    /************************************************************************
     * Creates a new {@code PersonName}. 
     *
     */
    public
    PersonName()
    {
        this( "","","" );
    }

    /************************************************************************
     * Creates a new {@code PersonName}. 
     *
     * @param first
     * @param last
     */
    public
    PersonName(String first,String last)
    {
        this( first,"",last );
    }
    
    /************************************************************************
     * Creates a new {@code PersonName}. 
     *
     * @param first
     * @param middle
     * @param last
     */
    public 
    PersonName(String first,String middle,String last)
    {
        itsFirstName = first;
        itsMiddleName = middle;
        itsLastName = last;
    }

    /************************************************************************
     * Creates a new {@code PersonName}. 
     *
     * @param other
     */
    public 
    PersonName(PersonName other)
    {
        this(other.itsFirstName,other.itsMiddleName,other.itsLastName);
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    @Override
    public PersonName 
    copy()
    {
        return new PersonName(this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object obj)
    {
        if ( obj instanceof PersonName )
        {
            PersonName other = (PersonName)obj;
            
            return 
                itsFirstName.equals( other.itsFirstName ) &&
                itsMiddleName.equals( other.itsMiddleName ) &&
                itsLastName.equals( other.itsLastName );
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
        
        hash = 31*hash + itsFirstName.hashCode();
        hash = 31*hash + itsMiddleName.hashCode();
        hash = 31*hash + itsLastName.hashCode();
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
        
        builder.append( itsFirstName );
        
        if ( !itsMiddleName.isEmpty() )
            builder.append( " " ).append( itsMiddleName );
        
        builder.append( " " ).append( itsLastName );
        return builder.toString();
    }

    /************************************************************************
     *  
     *
     * @param first
     */
    public void
    setFirstName(String first)
    {
        itsFirstName = first;
    }
    
    /************************************************************************
     *  
     *
     * @param middle
     */
    public void
    setMiddleName(String middle)
    {
        itsMiddleName = middle;
    }
    
    /************************************************************************
     *  
     *
     * @param last
     */
    public void
    setLastName(String last)
    {
        itsLastName = last;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getFirstName()
    {
        return itsFirstName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getMiddleName()
    {
        return itsMiddleName;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getLastName()
    {
        return itsLastName;
    }
}

// ##########################################################################
