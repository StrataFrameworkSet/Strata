// ##########################################################################
// # File Name:	InMemoryPersonRepositoryTest.java
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

package strata1.entity.inmemorytestdomain;

import strata1.entity.inmemoryrepository.InMemoryFinder;
import strata1.entity.inmemoryrepository.InMemoryRepositoryContext;
import strata1.entity.inmemoryrepository.InMemoryRepositoryProvider;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.testdomain.IPerson;
import strata1.entity.testdomain.PersonRepositoryTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryPersonRepositoryTest
    extends PersonRepositoryTest
{

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    InMemoryPersonRepositoryTest()
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
        
        new InMemoryFinder<IPerson>(
            context, 
            "HasPersonWithName", 
            IPerson.class, 
            new PersonByNamePredicate() );
        
        return context;
    }

    @Override
    protected IRepositoryProvider<Long,IPerson> 
    createProvider(IRepositoryContext context)
    {
        InMemoryRepositoryProvider<Long,IPerson> provider = 
            new InMemoryRepositoryProvider<Long,IPerson>(
                IPerson.class, 
                (InMemoryRepositoryContext)context, 
                new PersonReplicator(), 
                new PartyKeyRetriever<IPerson>());
            
        return provider;
    }

}

// ##########################################################################
