//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.event;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import io.vertx.kafka.client.producer.KafkaWriteStream;
import io.vertx.kafka.client.producer.impl.KafkaProducerImpl;
import io.vertx.kafka.client.producer.impl.KafkaWriteStreamImpl;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import strata.application.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.NullMapper;

import java.util.Map;
import java.util.Properties;

public
class KafkaAvroEventSender<E>
    extends AbstractEventSender<E,E>
{
    private final Vertx              itsVertx;
    private final Properties itsProperties;
    private final IActionQueue       itsQueue;
    private final String itsTopic;
    private final Class<E> itsType;
    private KafkaProducer<String,E>  itsProducer;

    public
    KafkaAvroEventSender(
        Vertx              v,
        Map<String,String> p,
        IActionQueue       q,
        Class<E> t,
        String topic)
    {
        super(new NullMapper<>());
        itsVertx = v;
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
            itsProducer = createKafkaProducer();
                /*
                KafkaProducer.create(
                    itsVertx,
                    itsProperties,
                    String.class,
                    itsType);
                */
        return this;
    }

    @Override
    public KafkaAvroEventSender<E>
    close()
    {
        if (isOpen())
        {
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
            () -> getProducer().write(createRecord(itsTopic,itsTopic,payload)));
    }

    protected KafkaProducer<String,E>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }

    protected KafkaProducerRecord<String,E>
    createRecord(String topic,String key,E payload)
    {
        return KafkaProducerRecord.create(itsTopic,itsTopic,payload);
    }

    protected Properties
    initializeProperties(Map<String,String> config)
    {
        Properties properties = new Properties();

        properties.putAll(config);
        properties.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());
        properties.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            KafkaAvroSerializer.class.getName());

        if (!properties.containsKey("schema.registry.url"))
            throw
                new IllegalStateException(
                    "schema.registry.url must be configured");

        return properties;
    }

    protected KafkaProducer<String,E>
    createKafkaProducer()
    {
        Serializer<Object> keySerializer = new KafkaAvroSerializer();
        Serializer<Object> valueSerializer = new KafkaAvroSerializer();
        KafkaWriteStream<String,E> stream = new KafkaWriteStreamImpl(itsVertx.getOrCreateContext(), new org.apache.kafka.clients.producer.KafkaProducer(itsProperties, keySerializer, valueSerializer));
        KafkaProducer<String,E> producer=(new KafkaProducerImpl(stream)).registerCloseHook();

        return producer;
    }
}

//////////////////////////////////////////////////////////////////////////////
