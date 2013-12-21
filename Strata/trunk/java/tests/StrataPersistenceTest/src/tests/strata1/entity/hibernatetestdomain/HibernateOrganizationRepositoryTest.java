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

package strata1.entity.hibernatetestdomain;

import strata1.entity.hibernaterepository.HibernateRepositoryContext;
import strata1.entity.hibernaterepository.HibernateRepositoryProvider;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.testdomain.IOrganization;
import strata1.entity.testdomain.OrganizationRepositoryTest;
import org.hibernate.cfg.Configuration;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateOrganizationRepositoryTest
    extends OrganizationRepositoryTest
{

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    HibernateOrganizationRepositoryTest()
    {
    }

    @Override
    protected IRepositoryContext 
    createContext()
    {
        Configuration configuration = new Configuration();
        
        configuration.configure();
        return new HibernateRepositoryContext(configuration.buildSessionFactory());
    }

    @Override
    protected IRepositoryProvider<Long,IOrganization> 
    createProvider(IRepositoryContext context)
    {
        return 
            new HibernateRepositoryProvider<Long,IOrganization>(
                IOrganization.class,(HibernateRepositoryContext)context );
    }

}

// ##########################################################################
