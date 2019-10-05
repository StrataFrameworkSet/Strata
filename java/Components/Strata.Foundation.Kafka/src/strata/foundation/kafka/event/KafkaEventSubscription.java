//////////////////////////////////////////////////////////////////////////////
// KafkaEventSubscription.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.vertx.kafka.client.consumer.KafkaReadStream;
import strata.foundation.core.event.IEventSubscription;

public
class KafkaEventSubscription
    implements IEventSubscription
{
    private final KafkaReadStream itsSource;

    public
    KafkaEventSubscription(KafkaReadStream source)
    {
        itsSource = source;
    }

    @Override
    public void
    request(long n)
    {
        itsSource.fetch(n);
    }

    @Override
    public void
    cancel()
    {

    }
}

//////////////////////////////////////////////////////////////////////////////
