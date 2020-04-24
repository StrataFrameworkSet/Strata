//////////////////////////////////////////////////////////////////////////////
// PhoneNumberTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.value;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public
class PhoneNumberTest
{
    private String itsInput;

    public
    PhoneNumberTest(String input)
    {
        itsInput = input;
    }
    @Parameterized.Parameters
    public static Iterable<? extends Object>
    data()
    {
        return
            Arrays.asList(
                "2355551234",
                "235-555-1234",
                "235.555.1234",
                "235 555 1234",
                "(235)5551234",
                "(235)555-1234",
                "(235)555.1234",
                "(235) 555 1234",
                "12355551234",
                "1-235-555-1234",
                "1.235.555.1234",
                "1 235 555 1234",
                "1(235)5551234",
                "1(235)555-1234",
                "1(235)555.1234",
                "1 (235) 555 1234");
    }

    @Test
    public void
    testNanpFormat()
    {
        new PhoneNumber(itsInput);

    }

    @Test
    public void
    testGetDigitsOnly()
    {
        System
            .out
            .println(new PhoneNumber(itsInput).getDigitsOnly());

        assertTrue(
            new PhoneNumber(itsInput)
                .getDigitsOnly()
                .chars()
                .allMatch(Character::isDigit));
    }
}

//////////////////////////////////////////////////////////////////////////////
