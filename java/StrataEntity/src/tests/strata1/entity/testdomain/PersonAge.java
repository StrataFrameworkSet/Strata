// ##########################################################################
// # File Name:	PersonAge.java
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

package strata1.entity.testdomain;

import strata1.common.datetime.DateTime;
import strata1.common.utility.ICopyable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonAge
    implements ICopyable
{
    private DateTime itsDateOfBirth;
    
    /************************************************************************
     * Creates a new {@code PersonAge}. 
     *
     */
    public 
    PersonAge()
    {
        this(new DateTime());
    }
    
    /************************************************************************
     * Creates a new {@code PersonAge}. 
     *
     * @param dateOfBirth
     */
    public 
    PersonAge(DateTime dateOfBirth)
    {
        itsDateOfBirth = dateOfBirth;
    }
    
    /************************************************************************
     * Creates a new {@code PersonAge}. 
     *
     * @param other
     */
    public
    PersonAge(PersonAge other)
    {
        this(other.itsDateOfBirth);
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PersonAge 
    copy()
    {
        return new PersonAge(this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object obj)
    {
        if ( obj instanceof PersonAge )
        {
            PersonAge other = (PersonAge)obj;
            
            return itsDateOfBirth.equals( other.itsDateOfBirth );
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
        
        hash = 31 * hash + itsDateOfBirth.hashCode();
        return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return "[" + getYears() + " years old]";
    }

    /************************************************************************
     *  
     *
     * @param dateOfBirth
     */
    public void
    setDateOfBirth(DateTime dateOfBirth)
    {
        itsDateOfBirth = dateOfBirth;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public DateTime
    getDateOfBirth()
    {
        return itsDateOfBirth;
    }

    public int 
    getYears()
    {
        DateTime now = new DateTime();
        
        return now.getYear() - itsDateOfBirth.getYear();
    }
}

// ##########################################################################
