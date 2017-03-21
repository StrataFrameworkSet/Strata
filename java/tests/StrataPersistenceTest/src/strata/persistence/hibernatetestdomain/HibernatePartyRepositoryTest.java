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

package strata.persistence.hibernatetestdomain;


import org.hibernate.cfg.Configuration;
import strata.persistence.hibernate.HibernateUnitOfWorkProvider;
import strata.persistence.testdomain.PartyRepositoryTest;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernatePartyRepositoryTest
    extends PartyRepositoryTest
{

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    HibernatePartyRepositoryTest()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("deprecation")
    @Override
    protected IUnitOfWorkProvider 
    createUnitOfWorkProvider()
    {
        Configuration configuration = new Configuration();
        
        configuration.configure();
        return new HibernateUnitOfWorkProvider(configuration.buildSessionFactory());
    }

}

// ##########################################################################
