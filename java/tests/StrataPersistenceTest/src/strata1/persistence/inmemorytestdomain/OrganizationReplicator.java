// ##########################################################################
// # File Name:	PersonReplicator.java
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

package strata1.persistence.inmemorytestdomain;

import strata1.persistence.inmemoryrepository.IEntityReplicator;
import strata1.persistence.testdomain.IContactInformation;
import strata1.persistence.testdomain.IOrganization;
import strata1.persistence.testdomain.Organization;
import java.security.SecureRandom;
import java.util.HashSet;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class OrganizationReplicator
    implements IEntityReplicator<Long,IOrganization>
{
    private SecureRandom itsGenerator;
    
    /************************************************************************
     * Creates a new {@code PersonReplicator}. 
     *
     */
    public 
    OrganizationReplicator() 
    {
        itsGenerator = new SecureRandom();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IOrganization 
    replicate(IOrganization entity)
    {
        IOrganization replicant = new Organization();
        
        replicant.setPartyKey( entity.getPartyKey() );
        replicant.setVersion( entity.getVersion() );
        
        replicant.setContactInformation( 
            new HashSet<IContactInformation>(
                entity.getContactInformation()));
        replicant.setName( entity.getName() );
        replicant.setMemberIds( 
            new HashSet<Long>(entity.getMemberIds()) );
        
        if ( mustGenerateKey(replicant.getPartyKey()) )
            replicant.setPartyKey( generateKey() );
        
        return replicant;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Long 
    generateKey()
    {
        Long key = itsGenerator.nextLong();
        
        if ( key == 0L)
            key = key + 1;
        
        return key < 0L ? -key : key;
    }

    /************************************************************************
     *  
     *
     * @param oldKey
     * @return
     */
    private boolean
    mustGenerateKey(Long oldKey)
    {
        return 
            oldKey == null ||
            oldKey == 0L;
    }
}

// ##########################################################################
