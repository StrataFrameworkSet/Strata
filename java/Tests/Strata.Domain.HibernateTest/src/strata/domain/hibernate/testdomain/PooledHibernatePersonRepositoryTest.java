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

package strata.domain.hibernate.testdomain;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import strata.domain.core.testdomain.PersonRepositoryTest;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.IUnitOfWorkProviderPool;
import strata.domain.core.unitofwork.ProxyUnitOfWorkProvider;
import strata.domain.core.unitofwork.UnitOfWorkProviderPool;
import strata.domain.hibernate.unitofwork.HibernateUnitOfWorkProvider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PooledHibernatePersonRepositoryTest
    extends PersonRepositoryTest
{
    private static IUnitOfWorkProviderPool theirPool;
    private static SessionFactory          theirSessionFactory;


    @BeforeClass
    public static void
    setUpStatic()
    {
        Configuration configuration = new Configuration();

        configuration.configure();

        theirSessionFactory = configuration.buildSessionFactory();
        theirPool =
            new UnitOfWorkProviderPool(
                8,
                () -> createHibernateUnitOfWorkProvider());
    }

    @AfterClass
    public static void
    tearDownStatic()
    {
        theirPool.clear();
        theirPool = null;
        theirSessionFactory.close();
        theirSessionFactory = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("deprecation")
    @Override
    protected IUnitOfWorkProvider
    createUnitOfWorkProvider()
    {
        return new ProxyUnitOfWorkProvider(theirPool);
    }

    protected static IUnitOfWorkProvider
    createHibernateUnitOfWorkProvider()
    {
        return new HibernateUnitOfWorkProvider(theirSessionFactory);
    }
}

// ##########################################################################
