// ##########################################################################
// # File Name:	Person.java
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

import strata.foundation.value.Money;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Person
    extends
        AbstractParty
    implements 
        IPerson
{
    private PersonName itsName;
    private PersonAge  itsAge;
    private Money      itsNetWorth;
    
    /************************************************************************
     * Creates a new {@code Person}. 
     *
     */
    public 
    Person()
    {
        super();
        itsName = new PersonName();
        itsAge  = new PersonAge();
        itsNetWorth = new Money( 0.00 );
    }

    /************************************************************************
     * Creates a new {@code Person}. 
     *
     * @param name
     * @param age
     */
    public
    Person(PersonName name,PersonAge age)
    {
        super();
        itsName = name;
        itsAge  = age;
        itsNetWorth = new Money( 0.00 );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object obj)
    {
        if ( obj instanceof IPerson )
        {
            IPerson other = (IPerson)obj;
            
            return
                getPartyKey().equals( other.getPartyKey() ) &&
                getName().equals( other.getName() ) &&
                getAge().equals( other.getAge() ) &&
                getNetWorth().equals( other.getNetWorth() );
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
        
        hash = hash*31 + getPartyKey().hashCode();
        hash = hash*31 + getName().hashCode();
        hash = hash*31 + getAge().hashCode();
        hash = hash*31 + getNetWorth().hashCode();
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
        
        builder.append( "[PartyKey=" ).append( getPartyKey() );
        builder.append( ",Name=" ).append( getName() );
        builder.append( ",Age=" ).append( getAge() );
        builder.append( ",NetWorth=" ).append( getNetWorth() ).append( "]" );
        return builder.toString();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setName(PersonName name)
    {
        itsName = name;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setAge(PersonAge age)
    {
        itsAge = age;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setNetWorth(Money netWorth)
    {
        itsNetWorth = netWorth;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PersonName 
    getName()
    {
        return itsName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PersonAge 
    getAge()
    {
        return itsAge;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Money 
    getNetWorth()
    {
        return itsNetWorth;
    }

}

// ##########################################################################
