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

package strata.persistence.inmemorytestdomain;


import java.security.SecureRandom;
import java.util.HashSet;
import strata.persistence.inmemory.IEntityReplicator;
import strata.persistence.testdomain.IContactInformation;
import strata.persistence.testdomain.IPerson;
import strata.persistence.testdomain.Person;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PersonReplicator
    implements IEntityReplicator<Long,IPerson>
{
    private SecureRandom itsGenerator;
    
    /************************************************************************
     * Creates a new {@code PersonReplicator}. 
     *
     */
    public 
    PersonReplicator() 
    {
        itsGenerator = new SecureRandom();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IPerson 
    replicate(IPerson entity)
    {
        IPerson replicant = new Person();
        
        replicant.setPartyKey( entity.getPartyKey() );
        replicant.setVersion( entity.getVersion() );
        
        replicant.setContactInformation( 
            new HashSet<IContactInformation>(
                entity.getContactInformation()));
        replicant.setName( entity.getName().copy() );
        replicant.setAge( entity.getAge().copy() );
        
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
        
        if ( key == 0L )
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
