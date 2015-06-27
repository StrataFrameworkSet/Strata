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

package strata1.persistence.inmemorytestdomain;

import strata1.persistence.inmemoryrepository.InMemoryFinder;
import strata1.persistence.inmemoryrepository.InMemoryRepositoryContext;
import strata1.persistence.inmemoryrepository.InMemoryRepositoryProvider;
import strata1.persistence.repository.IRepositoryContext;
import strata1.persistence.repository.IRepositoryProvider;
import strata1.persistence.testdomain.IOrganization;
import strata1.persistence.testdomain.IParty;
import strata1.persistence.testdomain.IPerson;
import strata1.persistence.testdomain.PartyRepositoryTest;

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

    @Override
    protected IRepositoryContext 
    createContext()
    {
        InMemoryRepositoryContext context = 
            new InMemoryRepositoryContext();

        new InMemoryFinder<IPerson>(
            context, 
            "GetByName", 
            IPerson.class, 
            new PersonByNamePredicate() );
        new InMemoryFinder<IOrganization>(
            context, 
            "GetByName", 
            IOrganization.class, 
            new OrganizationByNamePredicate() );
        
        return context;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IParty> 
    createPartyProvider(IRepositoryContext context)
    {
        return 
            new InMemoryRepositoryProvider<Long,IParty>(
                IParty.class, 
                (InMemoryRepositoryContext)context, 
                new PartyReplicator(), 
                new PartyKeyRetriever<IParty>());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IPerson> 
    createPersonProvider(IRepositoryContext context)
    {
        InMemoryRepositoryProvider<Long,IPerson> provider = 
            new InMemoryRepositoryProvider<Long,IPerson>(
                IPerson.class, 
                (InMemoryRepositoryContext)context, 
                new PersonReplicator(), 
                new PartyKeyRetriever<IPerson>());

        return provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IOrganization> 
    createOrganizationProvider(IRepositoryContext context)
    {
        InMemoryRepositoryProvider<Long,IOrganization> provider = 
            new InMemoryRepositoryProvider<Long,IOrganization>(
                IOrganization.class, 
                (InMemoryRepositoryContext)context, 
                new OrganizationReplicator(), 
                new PartyKeyRetriever<IOrganization>());

        return provider;
    }

}

// ##########################################################################
