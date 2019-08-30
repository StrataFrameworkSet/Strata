// ##########################################################################
// # File Name:	InMemoryPartyRepositoryTest.java
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

package strata.domain.inmemory.testdomain;


import strata.domain.core.testdomain.IOrganization;
import strata.domain.core.testdomain.IPerson;
import strata.domain.core.testdomain.PartyRepositoryTest;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.inmemory.GetAllPredicate;
import strata.domain.inmemory.InMemoryNamedQuery;
import strata.domain.inmemory.InMemoryUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryPartyRepositoryTest
    extends PartyRepositoryTest
{

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    InMemoryPartyRepositoryTest()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IUnitOfWorkProvider
    createUnitOfWorkProvider()
    {
        InMemoryUnitOfWorkProvider provider =
            new InMemoryUnitOfWorkProvider();

        provider
            .insertReplicator( IPerson.class,new PersonReplicator() )
            .insertRetriever( IPerson.class,new PartyKeyRetriever<IPerson>() )
            .insertReplicator( IOrganization.class,new OrganizationReplicator() )
            .insertRetriever( IOrganization.class,new PartyKeyRetriever<IOrganization>() );
        new InMemoryNamedQuery<IPerson>(
            provider, 
            "GetAll", 
            IPerson.class, 
            new GetAllPredicate<IPerson>() );
        
        new InMemoryNamedQuery<IPerson>(
            provider, 
            "GetByName", 
            IPerson.class, 
            new PersonByNamePredicate() );

        new InMemoryNamedQuery<IPerson>(
            provider, 
            "HasPersonWithName", 
            IPerson.class, 
            new PersonByNamePredicate() );
        
        new InMemoryNamedQuery<IOrganization>(
            provider, 
            "GetAll", 
            IOrganization.class, 
            new GetAllPredicate<IOrganization>() );
        
        new InMemoryNamedQuery<IOrganization>(
            provider, 
            "GetByName", 
            IOrganization.class, 
            new OrganizationByNamePredicate() );
        
        new InMemoryNamedQuery<IOrganization>(
            provider, 
            "HasOrganizationWithName", 
            IOrganization.class, 
            new OrganizationByNamePredicate() );
        
        return provider;
    }

}

// ##########################################################################
