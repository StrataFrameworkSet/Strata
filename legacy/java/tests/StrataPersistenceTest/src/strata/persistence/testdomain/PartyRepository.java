// ##########################################################################
// # File Name:	PartyRepository.java
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

import java.util.ArrayList;
import java.util.List;
import strata.persistence.repository.AbstractRepository;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PartyRepository
    extends    AbstractRepository<Long,IParty>
    implements IPartyRepository
{
    
    /************************************************************************
     * Creates a new {@code PartyRepository}. 
     *
     * @param provider
     */
    public 
    PartyRepository(IUnitOfWorkProvider provider)
    {
        super( Long.class,IParty.class,provider );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IParty 
    getPartyByKey(Long partyKey)
    {
        return getUnitOfWork().getExisting( getEntityType(),partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<IParty> 
    getMembersOf(IOrganization organization)
    {
        List<IParty> members = new ArrayList<IParty>();
        
        for (Long key : organization.getMemberIds())
        {
            IParty member = getPartyByKey( key );
            
            if ( member != null )
                members.add( member );
        }
        
        return members;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasPartyWithKey(Long partyKey)
    {
        return getUnitOfWork().hasExisting( getEntityType(),partyKey );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasMembersOf(IOrganization organization)
    {
        for (Long key : organization.getMemberIds())
        {
            if ( hasPartyWithKey( key ) )
                return true;
        }
        
        return false;
    }

}

// ##########################################################################
