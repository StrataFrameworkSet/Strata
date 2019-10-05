//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.NullMapper;

import java.util.HashMap;
import java.util.Map;

public
class KafkaAvroEventSender<E>
    extends AbstractEventSender<E,E>
{
    private final Map<String,Object> itsProperties;
    private final IActionQueue       itsQueue;
    private final String             itsTopic;
    private final Class<E>           itsType;
    private Producer<String,E>       itsProducer;

    public
    KafkaAvroEventSender(
        Map<String,Object> p,
        IActionQueue       q,
        Class<E>           t,
        String             topic)
    {
        super(new NullMapper<>());
        itsProperties = initializeProperties(p);
        itsQueue = q;
        itsTopic = topic;
        itsQueue.register(() -> open(),() -> close());
        itsType = t;
        itsProducer = null;
    }

    @Override
    public KafkaAvroEventSender<E>
    open()
    {
        if (!isOpen())
        {
            itsProducer = createKafkaProducer();
        }

        return this;
    }

    @Override
    public KafkaAvroEventSender<E>
    close()
    {
        if (isOpen())
        {
            itsProducer.flush();
            itsProducer.close();
            itsProducer = null;
        }

        return this;
    }

    @Override
    public boolean
    isOpen()
    {
        return itsProducer != null;
    }

    @Override
    protected void
    sendPayload(final E payload)
    {
        itsQueue.insert(
            () ->
                getProducer()
                    .send(
                        createRecord(itsTopic,payload),
                        (result,exception) ->
                        {
                            if (result == null)
                                exception.printStackTrace();
                        }));
    }

    protected Producer<String,E>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }

    protected ProducerRecord<String,E>
    createRecord(String topic,E payload)
    {
        return new ProducerRecord<>(itsTopic,payload);
    }

    protected Map<String,Object>
    initializeProperties(Map<String,Object> config)
    {
        Map<String,Object> properties = new HashMap<>();

        properties.putAll(config);
        properties.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());
        properties.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());

        if (!properties.containsKey(
            KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG))
            throw
                new IllegalStateException(
                    "schema.registry.url must be configured");

        return properties;
    }

    protected Producer<String,E>
    createKafkaProducer()
    {
        return new KafkaProducer<>(itsProperties);
    }
}

//////////////////////////////////////////////////////////////////////////////
