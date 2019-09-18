//////////////////////////////////////////////////////////////////////////////
// KafkaAvroFooEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.FooEvent;
import strata.foundation.core.event.IFooEventSender;
import strata.foundation.core.utility.JsonObjectMapper;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public
class KafkaFooEventSender
    extends KafkaEventSender<FooEvent>
    implements IFooEventSender
{
    @Inject
    public KafkaFooEventSender(IActionQueue q)
    {
        super(
            getProperties(),
            new JsonObjectMapper<>(),
            q,
            "foo.events");
    }

    private static Map<String,Object>
    getProperties()
    {
        return
            new HashMap<>()
            {{
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev-kafka.aws.hautelook.net:9093");
                put(ProducerConfig.ACKS_CONFIG, "1");
                put(ProducerConfig.RETRIES_CONFIG,"5");
                put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SSL");
            }};
    }
}

//////////////////////////////////////////////////////////////////////////////
