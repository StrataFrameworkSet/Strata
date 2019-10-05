//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventSubscriber.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import org.reactivestreams.Subscription;
import strata.foundation.core.event.IEventSubscriber;

public
class KafkaAvroEventSubscriber<E>
    implements IEventSubscriber<E>
{
    @Override
    public void
    onSubscribe(Subscription s)
    {

    }

    @Override
    public void
    onNext(E e)
    {

    }

    @Override
    public void
    onError(Throwable t)
    {

    }

    @Override
    public void
    onComplete()
    {

    }
}

//////////////////////////////////////////////////////////////////////////////
