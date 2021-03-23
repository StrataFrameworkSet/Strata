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

package strata.domain.hibernate.testdomain;

import org.hibernate.SessionFactory;
import strata.domain.core.testdomain.OrganizationRepositoryTest;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.hibernate.inject.PropertiesBasedSessionFactoryProvider;
import strata.domain.hibernate.unitofwork.HibernateUnitOfWorkProvider;

import java.util.Arrays;
import java.util.Properties;

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
    private static SessionFactory theirSessionFactory =
        createSessionFactory();

    /************************************************************************
     * Creates a new {@code InMemoryOrganizationRepositoryTest}. 
     *
     */
    public 
    HibernateOrganizationRepositoryTest()
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
        return new HibernateUnitOfWorkProvider(theirSessionFactory);

        /*
        Configuration configuration = new Configuration();
        
        configuration.configure();
        return new HibernateUnitOfWorkProvider(configuration.buildSessionFactory());
         */
    }

    private static SessionFactory
    createSessionFactory()
    {
        Properties properties = new Properties();

        properties.setProperty(
            "hibernate.connection.driver_class",
            "com.mysql.cj.jdbc.Driver");
        properties.setProperty(
            "hibernate.connection.url",
            "jdbc:mysql://localhost:3306/stratatest");
        properties.setProperty(
            "hibernate.connection.username",
            "development");
        properties.setProperty(
            "hibernate.connection.password",
            "development");
        properties.setProperty(
            "hibernate.default_schema",
            "denimtestdomain");
        properties.setProperty(
            "hibernate.dialect",
            "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty(
            "hibernate.bytecode.provider",
            "javassist");
        properties.setProperty(
            "hbm2ddl.auto",
            "update");
        properties.setProperty(
            "hibernate.c3p0.min_size",
            "4");
        properties.setProperty(
            "hibernate.c3p0.max_size",
            "16");
        properties.setProperty(
            "hibernate.c3p0.timeout",
            "0");
        properties.setProperty(
            "hibernate.c3p0.max_statements",
            "50");
        properties.setProperty(
            "hibernate.c3p0.idle_test_period",
            "3000");
        properties.setProperty(
            "non-hibernate.property",
            "foobar");

        return
            new PropertiesBasedSessionFactoryProvider(
                properties,
                Arrays.asList(
                    "strata/domain/hibernate/testdomain/configuration2/IParty.hbm.xml",
                    "strata/domain/hibernate/testdomain/configuration2/IContactInformation.hbm.xml",
                    "strata/domain/hibernate/testdomain/configuration2/EmailAddress.hbm.xml",
                    "strata/domain/hibernate/testdomain/configuration2/PhoneNumber.hbm.xml")).get();

    }
}

// ##########################################################################
