//////////////////////////////////////////////////////////////////////////////
// KafkaAvroEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import strata.foundation.core.event.AbstractEventReceiver;
import strata.foundation.core.event.IEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public
class KafkaAvroEventReceiver<E,L extends IEventListener<E>>
    extends AbstractEventReceiver<E,L>
{
    private final Map<String,Object> itsProperties;
    private final Class<E>           itsType;
    private final String             itsTopic;
    private Consumer<String,E>       itsConsumer;
    private ExecutorService          itsExecutor;
    private AtomicBoolean            itsListening;

    public
    KafkaAvroEventReceiver(
        Map<String,Object> p,
        Class<E>           t,
        String             topic)
    {
        itsProperties = initializeProperties(p);
        itsType       = t;
        itsTopic      = topic;
        itsConsumer   = null;
        itsExecutor   = Executors.newSingleThreadExecutor();
        itsListening  = new AtomicBoolean(false);
    }

    @Override
    public void
    startListening()
    {
        if (isListening())
            return;

        if (!hasListener())
            throw new IllegalStateException("No listener.");

        itsListening.set(true);
        itsConsumer = createConsumer();
        itsExecutor.execute(
            () ->
            {
                try
                {
                    itsConsumer.subscribe(Arrays.asList(itsTopic));

                    while (itsListening.get())
                    {
                        ConsumerRecords<String,E> records =
                            itsConsumer.poll(100);

                        for (ConsumerRecord<String,E> record: records)
                        {
                            try
                            {
                                 getListener().onEvent(record.value());
                            }
                            catch (Exception exception)
                            {
                                getListener().onException(exception);
                            }
                        }
                    }
                }
                catch (WakeupException wakeup) {}
                catch (Throwable exception)
                {
                    exception.printStackTrace();
                }
                finally
                {
                    if (itsConsumer != null)
                        itsConsumer.close();

                    itsConsumer = null;
                }
            }
        );
     }

    @Override
    public void
    stopListening()
    {
        itsListening.set(false);

        if (itsConsumer != null)
            itsConsumer.wakeup();
    }

    @Override
    public boolean
    isListening()
    {
        return (itsListening.get() == true) && (itsConsumer != null);
    }

    protected KafkaConsumer<String,E>
    createConsumer()
    {
        return new KafkaConsumer<String,E>(itsProperties);
    }

    protected Map<String,Object>
    initializeProperties(Map<String,Object> config)
    {
        Map<String,Object> properties = new HashMap<>();

        properties.putAll(config);
        properties.put(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName());
        properties.put(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            KafkaAvroDeserializer.class.getName());
        properties.put(
            KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
            true);
        properties.put(
            ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,
            true);

        if (
            !properties.containsKey(
                KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG))
            throw
                new IllegalStateException(
                    "schema.registry.url must be configured");

        return properties;
    }
}

//////////////////////////////////////////////////////////////////////////////
