//////////////////////////////////////////////////////////////////////////////
// IEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

public
interface IEventReceiver<E,L extends IEventListener<E>>
{
    IEventReceiver<E,L>
    setListener(L listener);

    L
    getListener();

    boolean
    hasListener();

    void
    startListening();

    void
    startListening(L listener);

    void
    stopListening();

    boolean
    isListening();

}

//////////////////////////////////////////////////////////////////////////////