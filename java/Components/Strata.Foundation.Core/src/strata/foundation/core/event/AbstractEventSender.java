//////////////////////////////////////////////////////////////////////////////
// AbstractEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.event;

import strata.foundation.core.utility.IObjectMapper;

public abstract
class AbstractEventSender<E,P>
    implements IEventSender<E>
{
    private final IObjectMapper<E,P> itsMapper;

    protected
    AbstractEventSender(IObjectMapper<E,P> mapper)
    {
        itsMapper = mapper;
    }

    @Override
    public AbstractEventSender<E,P>
    send(E event)
    {
        sendPayload(itsMapper.toPayload(event));
        return this;
    }

    protected abstract void
    sendPayload(P payload);
}

//////////////////////////////////////////////////////////////////////////////
