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
import strata.domain.hibernate.testdomain.PooledHibernatePersonRepositoryTest;
import strata.foundation.kafka.event.KafkaAvroEventSenderTest;
import strata.foundation.kafka.event.KafkaJsonEventSenderTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    HibernatePartyRepositoryTest.class,
    HibernatePersonRepositoryTest.class,
    HibernateOrganizationRepositoryTest.class,
    PooledHibernatePersonRepositoryTest.class,
    UnitOfWorkInterceptorTest.class,
    KafkaAvroEventSenderTest.class,
    KafkaJsonEventSenderTest.class})
public
class IntegrationSuite
{}

//////////////////////////////////////////////////////////////////////////////
