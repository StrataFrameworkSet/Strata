//////////////////////////////////////////////////////////////////////////////
// KafkaEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.vertx.core.Vertx;
import io.vertx.kafka.client.producer.KafkaProducer;
import io.vertx.kafka.client.producer.KafkaWriteStream;
import org.apache.kafka.clients.producer.ProducerRecord;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.event.AbstractEventSender;
import strata.foundation.core.utility.IObjectMapper;

import java.util.Map;

public
class VertxKafkaEventSender<E>
    extends AbstractEventSender<E,String>
{
    private final Vertx                  itsVertx;
    private final Map<String,Object>     itsProperties;
    private final IActionQueue           itsQueue;
    private final String                 itsTopic;
    private KafkaProducer<String,String> itsProducer;
    private KafkaWriteStream<String,String> itsStream;

    public VertxKafkaEventSender(
        Vertx                        v,
        Map<String,Object>           p,
        IObjectMapper<E,String>      m,
        IActionQueue                 q,
        String                       topic)
    {
        super(m);
        itsVertx = v;
        itsProperties = p;
        itsQueue = q;
        itsTopic = topic;
        itsQueue.register(() -> open(),() -> close());
        itsProducer = null;
        itsStream = null;
    }

    @Override
    public VertxKafkaEventSender<E>
    open()
    {
        if (!isOpen())
        {
            itsStream =
                KafkaWriteStream
                    .create(
                        itsVertx,
                        itsProperties,
                        String.class,
                        String.class);

            itsStream.exceptionHandler(e -> e.printStackTrace());

        }
            /*
            itsProducer =
                KafkaProducer
                    .create(
                        itsVertx,
                        itsProperties,
                        String.class,
                        String.class)
                    .exceptionHandler(exception -> exception.printStackTrace());
                */
        return this;
    }

    @Override
    public VertxKafkaEventSender<E>
    close()
    {
        if (isOpen())
        {
            itsStream.flush(result -> System.out.println("message(s) sent"));
            itsStream.close();
            itsStream = null;
            /*
            itsProducer.flush(result -> System.out.println("message(s) sent"));
            itsProducer.close();
            itsProducer = null;

             */
        }

        return this;
    }

    @Override
    public boolean
    isOpen()
    {
        return itsStream != null;
    }

    @Override
    protected void
    sendPayload(final String payload)
    {
        itsQueue.insert(
            () ->
                getStream()
                    .write(
                        new ProducerRecord<>(itsTopic,payload),
                        result ->
                        {
                            if (result.succeeded())
                                System.out.println("sendPayload: succeeded");
                            else if (result.failed())
                            {
                                System.out.println(
                                    "sendPlayload: failed: " + result.cause());
                                result.cause().printStackTrace();
                            }
                        }));
        /*
        itsQueue.insert(
            () ->
                getProducer().write(
                    KafkaProducerRecord.create(itsTopic,payload),
                    result ->
                    {
                        if (result.succeeded())
                            System.out.println("sendPayload: succeeded");
                        else if (result.failed())
                        {
                            System.out.println(
                                "sendPlayload: failed: " + result.cause());
                            result.cause().printStackTrace();
                        }
                    }));

         */
    }

    protected KafkaProducer<String,String>
    getProducer()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsProducer;
    }

    protected KafkaWriteStream<String,String>
    getStream()
    {
        if (!isOpen())
            throw new IllegalStateException("sender not open");

        return itsStream;
    }
}

//////////////////////////////////////////////////////////////////////////////
