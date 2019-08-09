//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.kafka.client.consumer.KafkaReadStream;
import io.vertx.kafka.client.consumer.impl.KafkaConsumerImpl;
import io.vertx.kafka.client.consumer.impl.KafkaReadStreamImpl;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import strata.foundation.core.event.AbstractEventReceiver;
import strata.foundation.core.event.IEventListener;

import java.util.Map;
import java.util.Properties;

public
class KafkaAvroEventReceiver<E,L extends IEventListener<E>>
    extends AbstractEventReceiver<E,L>
{
    private final Vertx             itsVertx;
    private final Properties itsProperties;
    private final Class<E> itsType;
    private final String itsTopic;
    private KafkaConsumer<String,E> itsConsumer;

    public KafkaAvroEventReceiver(
        Vertx                   v,
        Map<String,String> p,
        Class<E> t,
        String topic)
    {
        itsVertx = v;
        itsProperties = initializeProperties(p);
        itsType = t;
        itsTopic = topic;
        itsConsumer = null;
    }

    @Override
    public void
    startListening()
    {
        if (isListening())
            return;

        if (!hasListener())
            throw new IllegalStateException("No listener.");

        itsConsumer = createConsumer();
     }

    @Override
    public void
    stopListening()
    {
        itsConsumer.close();
        itsConsumer = null;
    }

    @Override
    public boolean
    isListening()
    {
        return itsConsumer != null;
    }

    protected KafkaConsumer<String,E>
    createConsumer()
    {
        return
            KafkaConsumer
                .create(itsVertx,itsProperties,String.class,itsType)
                .handler(createHandler())
                .subscribe(itsTopic);
    }

    protected Handler<KafkaConsumerRecord<String,E>>
    createHandler()
    {
        return (KafkaConsumerRecord<String,E> message) ->
        {
            try
            {
                message
                    .headers()
                    .stream()
                    .filter( kafkaHeader -> kafkaHeader.key().equals("Provided"))
                    .findFirst()
                    .ifPresent( header -> getListener().onEvent(message.value()));
            }
            catch (Exception e)
            {
                getListener().onException(e);
            }
        };
    }


    protected Properties
    initializeProperties(Map<String,String> config)
    {
        Properties properties = new Properties();

        properties.putAll(config);
        properties.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class);
        properties.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class);

        if (!properties.containsKey("schema.registry.url"))
            throw
                new IllegalStateException(
                    "schema.registry.url must be configured");

        return properties;
    }

    protected KafkaConsumer<String,E>
    createKafkaConsumer()
    {
        Deserializer<Object> keySerializer = new KafkaAvroDeserializer();
        Deserializer<Object> valueSerializer = new KafkaAvroDeserializer();
        KafkaReadStream<String,E> stream = new KafkaReadStreamImpl(itsVertx.getOrCreateContext(), new org.apache.kafka.clients.consumer.KafkaConsumer<>(itsProperties,keySerializer,valueSerializer));
        KafkaConsumer<String,E> consumer=(new KafkaConsumerImpl(stream)).registerCloseHook();

        return consumer;

    }
}

//////////////////////////////////////////////////////////////////////////////
