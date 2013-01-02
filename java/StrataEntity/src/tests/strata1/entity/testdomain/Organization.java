// ##########################################################################
// # File Name:	Organization.java
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

import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Organization
    extends    AbstractParty
    implements IOrganization
{
    private String    itsName;
    private Set<Long> itsMemberIds;
 
    /************************************************************************
     * Creates a new {@code Organization}. 
     *
     */
    public
    Organization()
    {
        this("");
    }
    
    /************************************************************************
     * Creates a new {@code Organization}. 
     *
     * @param name
     */
    public 
    Organization(String name)
    {
        itsName = name;
        itsMemberIds = new HashSet<Long>();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object obj)
    {
        if ( obj instanceof IOrganization )
        {
            IOrganization other = (IOrganization)obj;
            
            return
                getPartyKey().equals( other.getPartyKey() ) &&
                getName().equals( other.getName() );      
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
        builder.append( ",Name=" ).append( getName() ).append( "]" );
        return builder.toString();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setName(String orgName)
    {
        itsName = orgName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setMemberIds(Set<Long> memberIds)
    {
        itsMemberIds = memberIds;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getName()
    {
        return itsName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<Long> 
    getMemberIds()
    {
        return itsMemberIds;
    }

}

// ##########################################################################
