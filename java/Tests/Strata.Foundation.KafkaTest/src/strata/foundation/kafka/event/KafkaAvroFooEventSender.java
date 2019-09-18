//////////////////////////////////////////////////////////////////////////////
// KafkaAvroFooEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.producer.ProducerConfig;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.FooEvent;
import strata.foundation.core.event.IFooEventSender;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public
class KafkaAvroFooEventSender
    extends KafkaAvroEventSender<FooEvent>
    implements IFooEventSender
{
    @Inject
    public
    KafkaAvroFooEventSender(IActionQueue q)
    {
        super(
            getProperties(),
            q,
            FooEvent.class,
            "foo.events.test");
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
                put(
                    KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                   "https://dev-kafka-schema.aws.hautelook.net:8081");
            }};
    }
}

//////////////////////////////////////////////////////////////////////////////
