//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.NullMapper;

import java.util.Map;
import java.util.Properties;

public
class VertxKafkaAvroEventSender<E>
    extends AbstractEventSender<E,E>
{
    private final Vertx              itsVertx;
    private final Properties         itsProperties;
    private final IActionQueue       itsQueue;
    private final String             itsTopic;
    private final Class<E>           itsType;
    private KafkaProducer<String,E>  itsProducer;

    public VertxKafkaAvroEventSender(
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
    public VertxKafkaAvroEventSender<E>
    open()
    {
        if (!isOpen())
        {
            itsProducer = createKafkaProducer();
        }

        return this;
    }

    @Override
    public VertxKafkaAvroEventSender<E>
    close()
    {
        if (isOpen())
        {
            itsProducer.flush(result -> {});
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
                    .write(
                        createRecord(itsTopic,payload),
                        result ->
                        {
                            if (result.succeeded())
                                System.out.println("sendPayload succeeded");
                            if (result.failed())
                                System.out.println("sendPayload failed: " + result.cause());
                        }));
    }

    protected KafkaProducer<String,E>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }

    protected KafkaProducerRecord<String,E>
    createRecord(String topic,E payload)
    {
        return KafkaProducerRecord.create(itsTopic,null,payload);
    }

    protected Properties
    initializeProperties(Map<String,String> config)
    {
        Properties properties = new Properties();

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

    protected KafkaProducer<String,E>
    createKafkaProducer()
    {
        return
            KafkaProducer.create(
                itsVertx,
                itsProperties);
        /*
        KafkaWriteStream<String,E> stream =
            new KafkaWriteStreamImpl(
                itsVertx.getOrCreateContext(),
                new org.apache.kafka.clients.producer.KafkaProducer(itsProperties));
        KafkaProducer<String,E> producer =
            (new KafkaProducerImpl(stream)).registerCloseHook();

        return producer;

         */
    }
}

//////////////////////////////////////////////////////////////////////////////
