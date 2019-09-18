//////////////////////////////////////////////////////////////////////////////
// KafkaAvroFooEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import strata.foundation.core.event.FooEvent;
import strata.foundation.core.event.IFooEventListener;
import strata.foundation.core.event.IFooEventReceiver;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public
class KafkaAvroFooEventReceiver
    extends    KafkaAvroEventReceiver<FooEvent,IFooEventListener>
    implements IFooEventReceiver
{
    @Inject
    public
    KafkaAvroFooEventReceiver()
    {
        super(getProperties(),FooEvent.class,"foo.events.test");
    }


    private static Map<String,Object>
    getProperties()
    {
        return
            new HashMap<>()
            {{
                put(ConsumerConfig.GROUP_ID_CONFIG,"foo.events.test.consumer-group-1");
                put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev-kafka.aws.hautelook.net:9093");
                put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG,"SSL");
                put(
                    KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG,
                    "https://dev-kafka-schema.aws.hautelook.net:8081");
            }};
    }

}

//////////////////////////////////////////////////////////////////////////////
