//////////////////////////////////////////////////////////////////////////////
// KafkaJsonFooEventDeserializer.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.mapper;

import strata.foundation.core.event.FooEvent;

public
class KafkaJsonFooEventDeserializer
    extends KafkaJsonDeserializer<FooEvent>
{
    public
    KafkaJsonFooEventDeserializer()
    {
        super(FooEvent.class);
    }
}

//////////////////////////////////////////////////////////////////////////////
