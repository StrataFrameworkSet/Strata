//////////////////////////////////////////////////////////////////////////////
// CommitStageSuite.java
//////////////////////////////////////////////////////////////////////////////

package strata.testsuite.commitstage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata.foundation.core.utility.CopyableTest;
import strata.foundation.core.utility.SynchronizerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    SynchronizerTest.class,
    CopyableTest.class})
public
class CommitStageSuite {}

//////////////////////////////////////////////////////////////////////////////
