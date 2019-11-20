//////////////////////////////////////////////////////////////////////////////
// CloseableCleaner.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import org.jboss.resteasy.plugins.server.Cleanable;

public
class CloseableCleaner
    implements Cleanable
{
    private final AutoCloseable itsCloseable;

    public
    CloseableCleaner(AutoCloseable closeable)
    {
        itsCloseable = closeable;
    }

    @Override
    public void
    clean()
    {
        try
        {
            itsCloseable.close();
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e);
        }
    }
}

//////////////////////////////////////////////////////////////////////////////
