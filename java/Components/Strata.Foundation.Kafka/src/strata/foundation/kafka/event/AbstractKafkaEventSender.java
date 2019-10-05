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
import strata.foundation.core.event.IEventSender;

import java.util.Map;

public abstract
class AbstractKafkaEventSender<E>
    implements IEventSender<E>
{
    private final Map<String,Object> itsProperties;
    private final IActionQueue       itsQueue;
    private final String             itsTopic;
    private final Class<E>           itsType;
    private Producer<String,E>       itsProducer;

    protected
    AbstractKafkaEventSender(
        Map<String,Object> properties,
        IActionQueue       queue,
        Class<E>           type,
        String             topic)
    {
        itsProperties = initializeProperties(properties);
        itsQueue = queue;
        itsTopic = topic;
        itsQueue.register(() -> open(),() -> close());
        itsType = type;
        itsProducer = null;
    }

    @Override
    public AbstractKafkaEventSender<E>
    open()
    {
        if (!isOpen())
        {
            itsProducer = createProducer();
        }

        return this;
    }

    @Override
    public AbstractKafkaEventSender<E>
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
    public IEventSender<E>
    send(E event)
    {
        itsQueue.insert(
            () ->
                getProducer()
                    .send(
                        createRecord(itsTopic,event),
                        (result,exception) ->
                        {
                            if (result == null)
                                exception.printStackTrace();
                        }));
        return this;
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

    protected Producer<String,E>
    createProducer()
    {
        return new KafkaProducer<>(itsProperties);
    }

    private static Map<String,Object>
    initializeProperties(Map<String,Object> properties)
    {
        properties.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class.getName());

        if (!properties.containsKey(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG))
            throw
                new IllegalStateException(
                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG +
                        " must be configured");

        if (usesAvroSerializer(properties))
            if (!properties.containsKey(
                KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG))
                throw
                    new IllegalStateException(
                        KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG +
                            " must be configured");

        return properties;
    }

    private static boolean
    usesAvroSerializer(Map<String,Object> properties)
    {
        return
            properties
                .get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)
                .toString().equals(KafkaAvroSerializer.class.toString());
    }
}

//////////////////////////////////////////////////////////////////////////////
