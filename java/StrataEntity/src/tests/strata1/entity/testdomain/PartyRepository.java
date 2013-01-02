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

package strata1.entity.testdomain;

import strata1.entity.repository.*;
import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PartyRepository
    implements IPartyRepository
{
    private IRepositoryProvider<Long,IParty> itsProvider;
    
    /************************************************************************
     * Creates a new {@code PartyRepository}. 
     *
     * @param provider
     */
    public 
    PartyRepository(IRepositoryProvider<Long,IParty> provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRepositoryContext 
    getContext()
    {
        return itsProvider.getContext();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IParty 
    getPartyByKey(Long partyKey)
    {
        return itsProvider.getExisting( partyKey );
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
        return itsProvider.hasExisting( partyKey );
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
