//////////////////////////////////////////////////////////////////////////////
// StringEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.vertx.event;

import io.vertx.core.Vertx;
import strata.application.core.action.IActionQueue;

import java.util.Map;

public
class StringEventSender
    extends KafkaAvroEventSender<String>
{

    public StringEventSender(
        Vertx v,
        Map<String,String> p,
        IActionQueue q,
        String topic)
    {
        super(v,p,q,String.class,topic);
    }
}

//////////////////////////////////////////////////////////////////////////////
