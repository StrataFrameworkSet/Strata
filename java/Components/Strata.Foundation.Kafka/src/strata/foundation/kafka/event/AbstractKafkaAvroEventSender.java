//////////////////////////////////////////////////////////////////////////////
// AbstractKafkaAvroEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import strata.foundation.core.action.IActionQueue;

import java.util.Map;

public abstract
class AbstractKafkaAvroEventSender<E>
    extends AbstractKafkaEventSender<E>
{
    protected
    AbstractKafkaAvroEventSender(
        Map<String,Object> properties,
        IActionQueue       queue,
        Class<E>           type,
        String             topic)
    {
        super(
            initializeProperties(properties),
            queue,
            type,
            topic);
    }

    private static Map<String,Object>
    initializeProperties(Map<String,Object> properties)
    {
        properties.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());

        return properties;
    }
}

//////////////////////////////////////////////////////////////////////////////
