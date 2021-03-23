//////////////////////////////////////////////////////////////////////////////
// PasswordGeneratorTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public
class ConfirmationCodeGeneratorTest
{
    private int                        itsLength;
    private int                        itsMinAlpha;
    private int                        itsMinNum;
    private IConfirmationCodeGenerator itsTarget;

    @Before
    public void
    setUp()
    {
        itsLength = 6;
        itsMinAlpha = 3;
        itsMinNum = 3;
        itsTarget =
            new DefaultConfirmationCodeGenerator(
                itsLength,
                itsMinAlpha,
                itsMinNum);
    }
    @Test
    public void
    testGetNextConfirmationCode()
    {
        String confirmationCode = itsTarget.getNextConfirmationCode();

        System
            .out
            .println("Confirmation Code = " + confirmationCode);

        checkConfirmationCode(confirmationCode);
    }

    private void
    checkConfirmationCode(String confirmationCode)
    {
        final AtomicInteger alphas = new AtomicInteger(0);
        final AtomicInteger nums = new AtomicInteger(0);

        assertEquals(itsLength,confirmationCode.length());

        confirmationCode
            .chars()
            .forEach(
                c ->
                {
                    if (Character.isAlphabetic(c))
                        alphas.incrementAndGet();
                    else if (Character.isDigit(c))
                        nums.incrementAndGet();
                    else
                        throw new IllegalStateException("Illegal character type");
                }
            );

        assertTrue(alphas.intValue() >= itsMinAlpha);
        assertTrue(nums.intValue() >= itsMinNum);
    }
}

//////////////////////////////////////////////////////////////////////////////
