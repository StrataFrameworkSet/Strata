//////////////////////////////////////////////////////////////////////////////
// HashedStringTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.value;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public
class HashedStringTest
{
    private HashedString foo;
    private HashedString bar;

    @Before
    public void
    setUp()
    {
        foo = new HashedString("XXXXXXX");
        bar = new HashedString("YYYYYYY");
    }

    @Test
    public void
    testMatches()
    {
        assertTrue(foo.matches("XXXXXXX"));
        assertFalse(foo.matches("XXXXXXY"));
        assertFalse(bar.matches("XXXXXXX"));
    }

    @Ignore
    @Test
    public void
    testToString()
    {
        assertEquals(foo.toString(),"");
    }

}

//////////////////////////////////////////////////////////////////////////////
