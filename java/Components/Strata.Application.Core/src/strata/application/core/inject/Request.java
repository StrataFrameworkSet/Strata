//////////////////////////////////////////////////////////////////////////////
// Operation.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.jboss.resteasy.core.ResteasyContext;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public
class Request
    implements AutoCloseable
{
    private final Injector             itsInjector;
    private final Queue<AutoCloseable> itsCloseables;

    public
    Request(Injector injector)
    {
        itsInjector = injector;
        itsCloseables = new ConcurrentLinkedQueue<>();
        ResteasyContext.addContextDataLevel();
        ResteasyContext.pushContext(Request.class,this);
    }

    public <T> T
    getInstance(Class<T> type)
    {
        T instance = itsInjector.getInstance(type);

        if (instance instanceof AutoCloseable)
            addCloseable((AutoCloseable)instance);

        return instance;
    }

    public <T> T
    getInstance(TypeLiteral<T> type)
    {
        T instance = itsInjector.getInstance(Key.get(type));

        if (instance instanceof AutoCloseable)
            addCloseable((AutoCloseable)instance);

        return instance;
    }

    public <T> T
    getInstance(Key<T> key)
    {
        T instance = itsInjector.getInstance(key);

        if (instance instanceof AutoCloseable)
            addCloseable((AutoCloseable)instance);

        return instance;
    }

    @Override
    public void
    close()
    {
        ResteasyContext.removeContextDataLevel();

        while (!itsCloseables.isEmpty())
        {
            AutoCloseable closeable = itsCloseables.remove();

            try
            {
                closeable.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    protected void
    addCloseable(AutoCloseable closeable)
    {
        if (!itsCloseables.contains(closeable))
            itsCloseables.add(closeable);
    }
}

//////////////////////////////////////////////////////////////////////////////
