//////////////////////////////////////////////////////////////////////////////
// SecureConfigurationTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.configuration;

import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public
class SecureConfigurationTest
{
    private IConfiguration itsTarget;

    @Before
    public void
    setUp()
        throws Exception
    {
        itsTarget = new SecureConfiguration(getPropertiesStream());
    }

    @Test
    public void
    testGetProperty()
    {
        String unencryptedProperty1 =
            itsTarget.getProperty("unencrypted.property1");
        String encryptedProperty1 =
            itsTarget.getProperty("encrypted.property1");
        String unencryptedProperty2 =
            itsTarget.getProperty("unencrypted.property2");
        String encryptedProperty2 =
            itsTarget.getProperty("encrypted.property2");

        assertNotNull(unencryptedProperty1);
        assertNotNull(encryptedProperty1);
        assertNotNull(unencryptedProperty2);
        assertNotNull(encryptedProperty2);
    }

    protected InputStream
    getPropertiesStream()
    {
        return
            ClassLoader
                .getSystemResourceAsStream(
                    "configurationtest.properties");
    }
}

//////////////////////////////////////////////////////////////////////////////
