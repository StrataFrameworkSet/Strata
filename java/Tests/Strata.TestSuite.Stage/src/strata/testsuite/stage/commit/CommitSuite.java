//////////////////////////////////////////////////////////////////////////////
// CommitSuite.java
//////////////////////////////////////////////////////////////////////////////

package strata.testsuite.stage.commit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata.application.core.interception.TimingMetricsInterceptorTest;
import strata.domain.inmemory.testdomain.InMemoryOrganizationRepositoryTest;
import strata.domain.inmemory.testdomain.InMemoryPartyRepositoryTest;
import strata.domain.inmemory.testdomain.InMemoryPersonRepositoryTest;
import strata.foundation.core.utility.CopyableTest;
import strata.foundation.core.utility.SynchronizerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    SynchronizerTest.class,
    CopyableTest.class,
    InMemoryPartyRepositoryTest.class,
    InMemoryPersonRepositoryTest.class,
    InMemoryOrganizationRepositoryTest.class,
    TimingMetricsInterceptorTest.class})
public
class CommitSuite
{}

//////////////////////////////////////////////////////////////////////////////
