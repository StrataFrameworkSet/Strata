//////////////////////////////////////////////////////////////////////////////
// IntegrationSuite.java
//////////////////////////////////////////////////////////////////////////////

package strata.testsuite.stage.integration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata.application.core.interception.UnitOfWorkInterceptorTest;
import strata.domain.hibernate.testdomain.HibernateOrganizationRepositoryTest;
import strata.domain.hibernate.testdomain.HibernatePartyRepositoryTest;
import strata.domain.hibernate.testdomain.HibernatePersonRepositoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    HibernatePartyRepositoryTest.class,
    HibernatePersonRepositoryTest.class,
    HibernateOrganizationRepositoryTest.class,
    UnitOfWorkInterceptorTest.class})
public
class IntegrationSuite
{}

//////////////////////////////////////////////////////////////////////////////
