//////////////////////////////////////////////////////////////////////////////
// AbstractCompletionSource.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.concurrent;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract
class AbstractCompletionSource<T>
    implements ICompletionSource<T>
{
    private final Set<ICompletionListener<T>> itsListeners;

    protected
    AbstractCompletionSource()
    {
        itsListeners = new ConcurrentSkipListSet<>();
    }

    @Override
    public T
    attach(ICompletionListener<T> listener)
    {
        itsListeners.add(listener);
        return getSelf();
    }

    @Override
    public T
    detach(ICompletionListener<T> listener)
    {
        itsListeners.remove(listener);
        return getSelf();
    }

    @Override
    public boolean
    isAttached(ICompletionListener<T> listener)
    {
        return itsListeners.contains(listener);
    }

    @Override
    public void
    complete()
    {
        itsListeners
            .stream()
            .forEach(listener -> listener.onComplete(getSelf()));
    }

    protected abstract T
    getSelf();
}

//////////////////////////////////////////////////////////////////////////////
