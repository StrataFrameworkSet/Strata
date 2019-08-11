//////////////////////////////////////////////////////////////////////////////
// DescriptionAsTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.testsuite;

import junit.framework.Test;
import junit.framework.TestResult;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.junit.runner.Description;

public
class DescriptionAsTest
    implements Test
{
    private final Description description;

    public DescriptionAsTest(Description description) {
        this.description = description;
    }

    public int countTestCases() {
        return 1;
    }

    public void run(TestResult result) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@link JUnitResultFormatter} determines the test name by reflection.
     */
    public String getName() {
        return description.getDisplayName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionAsTest that = (DescriptionAsTest) o;

        if (!description.equals(that.description)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}

//////////////////////////////////////////////////////////////////////////////
