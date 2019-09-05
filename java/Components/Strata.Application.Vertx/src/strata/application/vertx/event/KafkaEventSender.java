//////////////////////////////////////////////////////////////////////////////
// KafkaEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.vertx.event;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaProducerRecord;
import strata.application.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Map;

public
class KafkaEventSender<E>
    extends AbstractEventSender<E,String>
{
    private final Vertx                  itsVertx;
    private final Map<String,String> itsProperties;
    private final IActionQueue           itsQueue;
    private final String itsTopic;
    private KafkaProducer<String,String> itsProducer;

    public
    KafkaEventSender(
        Vertx                        v,
        Map<String,String> p,
        IObjectMapper<E,String>      m,
        IActionQueue                 q,
        String topic)
    {
        super(m);
        itsVertx = v;
        itsProperties = p;
        itsQueue = q;
        itsTopic = topic;
        itsQueue.register(() -> open(),() -> close());
        itsProducer = null;

    }

    @Override
    public KafkaEventSender<E>
    open()
    {
        if (!isOpen())
            itsProducer =
                KafkaProducer.create(
                    itsVertx,
                    itsProperties,
                    String.class,
                    String.class);

        return this;
    }

    @Override
    public KafkaEventSender<E>
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
    sendPayload(final String payload)
    {
        itsQueue.insert(
            () ->
                getProducer().write(
                    KafkaProducerRecord.create(itsTopic,payload)));
    }

    protected KafkaProducer<String,String>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }
}

//////////////////////////////////////////////////////////////////////////////
