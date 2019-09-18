//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import strata.foundation.core.event.AbstractEventReceiver;
import strata.foundation.core.event.IEventListener;

import java.util.Map;
import java.util.Properties;

public
class VertxKafkaAvroEventReceiver<E,L extends IEventListener<E>>
    extends AbstractEventReceiver<E,L>
{
    private final Vertx             itsVertx;
    private final Properties itsProperties;
    private final Class<E> itsType;
    private final String itsTopic;
    private KafkaConsumer<String,E> itsConsumer;

    public VertxKafkaAvroEventReceiver(
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
            createKafkaConsumer()
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
                getListener().onEvent(message.value());
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
            StringDeserializer.class.getName());
        properties.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class.getName());

        if (!properties.containsKey("schema.registry.url"))
            throw
                new IllegalStateException(
                    "schema.registry.url must be configured");

        return properties;
    }

    protected KafkaConsumer<String,E>
    createKafkaConsumer()
    {

        return
            KafkaConsumer.create(
                itsVertx,
                itsProperties);

        /*
        KafkaReadStream<String,E> stream =
            new KafkaReadStreamImpl(
                itsVertx.getOrCreateContext(),
                new org.apache.kafka.clients.consumer.KafkaConsumer<>(itsProperties));
        KafkaConsumer<String,E> consumer =
            (new KafkaConsumerImpl(stream)).registerCloseHook();

        return consumer;

         */
    }
}

//////////////////////////////////////////////////////////////////////////////
