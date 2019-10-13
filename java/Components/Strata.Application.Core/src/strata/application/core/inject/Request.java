//////////////////////////////////////////////////////////////////////////////
// Operation.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.jboss.resteasy.core.ResteasyContext;

public
class Request
    implements AutoCloseable
{
    private final Injector  itsInjector;

    public
    Request(Injector injector)
    {
        itsInjector = injector;
        ResteasyContext.addContextDataLevel();
    }

    public <T> T
    getInstance(Class<T> type)
    {
        return itsInjector.getInstance(type);
    }

    public <T> T
    getInstance(TypeLiteral<T> type)
    {
        return (T)itsInjector.getInstance(Key.get(type));
    }

    public <T> T
    getInstance(Key<T> key)
    {
        return itsInjector.getInstance(key);
    }

    @Override
    public void
    close()
    {
        ResteasyContext.removeContextDataLevel();
    }
}

//////////////////////////////////////////////////////////////////////////////
