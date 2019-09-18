//////////////////////////////////////////////////////////////////////////////
// KafkaEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Map;

public
class KafkaEventSender<E>
    extends AbstractEventSender<E,String>
{
    private final Map<String,Object> itsProperties;
    private final IActionQueue       itsQueue;
    private final String             itsTopic;
    private Producer<String,String>  itsProducer;


    public
    KafkaEventSender(
        Map<String,Object>       p,
        IObjectMapper<E,String>  m,
        IActionQueue             q,
        String                   topic)
    {
        super(m);
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
                new KafkaProducer<>(
                        itsProperties,
                        new StringSerializer(),
                        new StringSerializer());

        return this;
    }

    @Override
    public KafkaEventSender<E>
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
    sendPayload(final String payload)
    {
        itsQueue.insert(
            () ->
                getProducer()
                    .send(
                        new ProducerRecord<>(itsTopic,payload),
                        (result,exception) ->
                        {
                            if (result == null)
                                exception.printStackTrace();
                        }));

    }

    protected Producer<String,String>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }

}

//////////////////////////////////////////////////////////////////////////////
