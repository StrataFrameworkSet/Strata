//////////////////////////////////////////////////////////////////////////////
// StageRunner.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.testsuite;

import org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

import java.io.File;
import java.io.FileOutputStream;

public
class StageRunner
{
    public static void
    main(String[] args)
        throws Exception
    {
        try
        {
            System.out.println("StageRunner begin");
            String testToRun =
                args.length == 2 ? args[0] : "";
            String reportDir =
                args.length == 2 ? args[1] : "test-results";
            Class<?> testSubject = null;

            JUnitCore runner = new JUnitCore();
            RunListener listener =
                new JunitResultFormatterAsRunListener(new XMLJUnitResultFormatter())
                {
                    @Override
                    public void
                    testStarted(Description description) throws Exception
                    {
                        super.testStarted(description);
                        formatter.setOutput(
                            new FileOutputStream(
                                new File(
                                    reportDir,
                                    "TEST-" + description.getDisplayName() + ".xml")));
                        super.testStarted(description);
                    }
                };

            System.out.println("debug 1");
            runner.addListener(listener);
            System.out.println("debug 2");
            testSubject = Class.forName(testToRun);
            System.out.println("debug 3");
            runner.run(testSubject);
            System.out.println("StageRunner end");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

   }
}

//////////////////////////////////////////////////////////////////////////////
