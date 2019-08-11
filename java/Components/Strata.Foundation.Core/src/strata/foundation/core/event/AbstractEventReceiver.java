//////////////////////////////////////////////////////////////////////////////
// AbstractEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

public abstract
class AbstractEventReceiver<E,L extends IEventListener<E>>
    implements IEventReceiver<E,L>
{
    private L itsListener;

    protected
    AbstractEventReceiver()
    {
        itsListener = null;
    }

    public AbstractEventReceiver<E,L>
    setListener(L listener)
    {
        itsListener = listener;
        return this;
    }

    public L
    getListener()
    {
        return itsListener;
    }

    public boolean
    hasListener()
    {
        return itsListener != null;
    }

    @Override
    public void
    startListening(L listener)
    {
        setListener(listener);
        startListening();
    }
}

//////////////////////////////////////////////////////////////////////////////
