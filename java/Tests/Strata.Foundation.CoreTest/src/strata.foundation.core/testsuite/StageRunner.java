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
import java.nio.file.Files;
import java.nio.file.Path;

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
                        Path parent = Path.of(reportDir);
                        File testResult =
                            new File(
                                reportDir,
                                "TEST-" + description.getDisplayName() + ".xml");

                        Files.createDirectories(parent);
                        testResult.createNewFile();

                        super.testStarted(description);
                        formatter.setOutput(new FileOutputStream(testResult));
                        super.testStarted(description);
                    }
                };

            runner.addListener(listener);
            System.out.println("Loading test subject: " + testToRun + "...");
            testSubject = Class.forName(testToRun);
            System.out.println("Running tests...");
            runner.run(testSubject);
            System.out.println("StageRunner end");
            System.exit(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }

   }
}

//////////////////////////////////////////////////////////////////////////////
