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
class PasswordGeneratorTest
{
    private int                itsLength;
    private int                itsMinAlpha;
    private int                itsMinNum;
    private int                itsMinSpecial;
    private IPasswordGenerator itsTarget;

    @Before
    public void
    setUp()
    {
        itsLength = 16;
        itsMinAlpha = 12;
        itsMinNum = 2;
        itsMinSpecial = 1;
        itsTarget =
            new DefaultPasswordGenerator(
                itsLength,
                itsMinAlpha,
                itsMinNum,
                itsMinSpecial);
    }
    @Test
    public void
    testGetNextPassword()
    {
        String password = itsTarget.getNextPassword();

        System
            .out
            .println("Password = " + password);

        checkPassword(password);
    }

    private void
    checkPassword(String password)
    {
        final AtomicInteger alphas = new AtomicInteger(0);
        final AtomicInteger nums = new AtomicInteger(0);
        final AtomicInteger specials = new AtomicInteger(0);

        assertEquals(itsLength,password.length());

        password
            .chars()
            .forEach(
                c ->
                {
                    if (Character.isAlphabetic(c))
                        alphas.incrementAndGet();
                    else if (Character.isDigit(c))
                        nums.incrementAndGet();
                    else
                        specials.incrementAndGet();
                }
            );

        assertTrue(alphas.intValue() >= itsMinAlpha);
        assertTrue(nums.intValue() >= itsMinNum);
        assertTrue(specials.intValue() >= itsMinSpecial);
    }
}

//////////////////////////////////////////////////////////////////////////////
