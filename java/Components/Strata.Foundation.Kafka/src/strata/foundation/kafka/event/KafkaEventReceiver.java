//////////////////////////////////////////////////////////////////////////////
// KafkaEventReceiver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.consumer.KafkaConsumer;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import strata.foundation.core.event.AbstractEventReceiver;
import strata.foundation.core.event.IEventListener;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Map;

public
class KafkaEventReceiver<E,L extends IEventListener<E>>
    extends AbstractEventReceiver<E,L>
{
    private final Vertx                   itsVertx;
    private final Map<String,String> itsProperties;
    private final IObjectMapper<E,String> itsMapper;
    private final Class<E> itsType;
    private final String itsTopic;
    private KafkaConsumer<String,String>  itsConsumer;

    public
    KafkaEventReceiver(
        Vertx                   v,
        Map<String,String> p,
        IObjectMapper<E,String> m,
        Class<E> t,
        String topic)
    {
        itsVertx = v;
        itsProperties = p;
        itsMapper = m;
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

    protected KafkaConsumer<String,String>
    createConsumer()
    {
        return
            KafkaConsumer
                .create(itsVertx,itsProperties,String.class,String.class)
                .handler(
                    (KafkaConsumerRecord<String,String> message) ->
                    {
                        try
                        {
                            getListener()
                                .onEvent(
                                    itsMapper.toObject(
                                        itsType,
                                        message.value()));
                        }
                        catch (Exception e)
                        {
                            getListener().onException(e);
                        }
                    })
                .subscribe(itsTopic);
    }
}

//////////////////////////////////////////////////////////////////////////////
