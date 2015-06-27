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

package strata1.persistence.hibernatetestdomain;

import strata1.persistence.hibernaterepository.HibernateRepositoryContext;
import strata1.persistence.hibernaterepository.HibernateRepositoryProvider;
import strata1.persistence.repository.IRepositoryContext;
import strata1.persistence.repository.IRepositoryProvider;
import strata1.persistence.testdomain.IOrganization;
import strata1.persistence.testdomain.IParty;
import strata1.persistence.testdomain.IPerson;
import strata1.persistence.testdomain.PartyRepositoryTest;
import org.hibernate.cfg.Configuration;

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

    @SuppressWarnings("deprecation")
    @Override
    protected IRepositoryContext 
    createContext()
    {
        Configuration configuration = new Configuration();
        
        configuration.configure();
        return new HibernateRepositoryContext(configuration.buildSessionFactory());
    }

    protected <T> IRepositoryProvider<Long,T> 
    createProvider(Class<T> type,IRepositoryContext context)
    {
        return 
            new HibernateRepositoryProvider<Long,T>(
                type,(HibernateRepositoryContext)context );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IParty> 
    createPartyProvider(IRepositoryContext context)
    {
        return createProvider( IParty.class,context );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IPerson> createPersonProvider(
        IRepositoryContext context)
    {
        return createProvider( IPerson.class,context );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IRepositoryProvider<Long,IOrganization> createOrganizationProvider(
        IRepositoryContext context)
    {
        return createProvider( IOrganization.class,context );
    }

}

// ##########################################################################
