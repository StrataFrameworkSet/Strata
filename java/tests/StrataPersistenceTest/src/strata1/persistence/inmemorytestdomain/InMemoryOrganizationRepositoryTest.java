// ##########################################################################
// # File Name:	InMemoryOrganizationRepositoryTest.java
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
import strata1.persistence.testdomain.OrganizationRepositoryTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryOrganizationRepositoryTest
    extends OrganizationRepositoryTest
{

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    InMemoryOrganizationRepositoryTest()
    {
    }

    @Override
    protected IRepositoryContext 
    createContext()
    {
        InMemoryRepositoryContext context = 
            new InMemoryRepositoryContext();

        new InMemoryFinder<IOrganization>(
            context, 
            "GetByName", 
            IOrganization.class, 
            new OrganizationByNamePredicate() );
        
        return context;
    }

    @Override
    protected IRepositoryProvider<Long,IOrganization> 
    createProvider(IRepositoryContext context)
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
