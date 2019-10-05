//////////////////////////////////////////////////////////////////////////////
// KafkaAvroFooEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.vertx.core.Vertx;
import strata.foundation.core.event.FooEvent;
import strata.foundation.core.event.IFooEventListener;
import strata.foundation.core.event.IFooEventReceiver;
import strata.foundation.core.utility.JsonObjectMapper;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public
class KafkaFooEventReceiver
    extends    KafkaEventReceiver<FooEvent,IFooEventListener>
    implements IFooEventReceiver
{
    @Inject
    public KafkaFooEventReceiver(Vertx v)
    {
        super(
            v,
            getProperties(),
            new JsonObjectMapper<>(),
            FooEvent.class,
            "foo.events");
    }


    private static Map<String,String>
    getProperties()
    {
        return
            new HashMap<String,String>()
            {{
                put("bootstrap.servers", "dev-kafka.aws.hautelook.net:9093");
                put("acks", "1");
            }};
    }

}

//////////////////////////////////////////////////////////////////////////////
