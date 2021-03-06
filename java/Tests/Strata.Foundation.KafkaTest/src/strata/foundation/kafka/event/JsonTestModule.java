//////////////////////////////////////////////////////////////////////////////
// JsonTestModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import io.vertx.core.Vertx;
import strata.foundation.core.action.IActionQueue;
import strata.foundation.core.action.StandardActionQueue;
import strata.foundation.core.event.IFooEventReceiver;
import strata.foundation.core.event.IFooEventSender;
import strata.foundation.core.inject.AbstractModule;
import strata.foundation.core.inject.GuiceThreadScope;

public
class JsonTestModule
    extends AbstractModule
{
    public JsonTestModule()
    {
        setDefaultScope(new GuiceThreadScope());
    }

    @Override
    protected void
    configure()
    {
        Vertx vertx = Vertx.vertx();
        bind(Vertx.class)
            .toInstance(vertx);

        bind(IActionQueue.class)
            .to(StandardActionQueue.class)
            .in(getDefaultScope());

        bind(IFooEventSender.class)
            .to(KafkaJsonFooEventSender.class)
            .in(getDefaultScope());

        bind(IFooEventReceiver.class)
            .to(KafkaJsonFooEventReceiver.class)
            .in(getDefaultScope());

    }
}

//////////////////////////////////////////////////////////////////////////////
