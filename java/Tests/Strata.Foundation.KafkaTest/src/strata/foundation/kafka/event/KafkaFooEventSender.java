//////////////////////////////////////////////////////////////////////////////
// KafkaAvroFooEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.FooEvent;
import strata.foundation.core.event.IFooEventSender;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public
class KafkaFooEventSender
    extends AbstractKafkaEventSender<FooEvent>
    implements IFooEventSender
{
    @Inject
    public
    KafkaFooEventSender(IActionQueue q)
    {
        super(
            getProperties(),
            q,
            e -> e.getSource().getId(),
            FooEvent.class,
            "foo.events");
    }

    private static Map<String,Object>
    getProperties()
    {
        return
            new HashMap<String,Object>()
            {{
                put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev-kafka.aws.hautelook.net:9093");
                put(ProducerConfig.ACKS_CONFIG, "1");
                put(ProducerConfig.RETRIES_CONFIG,"5");
                put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SSL");
            }};
    }
}

//////////////////////////////////////////////////////////////////////////////
