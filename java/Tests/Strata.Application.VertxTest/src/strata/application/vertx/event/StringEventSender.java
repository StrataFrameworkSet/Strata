//////////////////////////////////////////////////////////////////////////////
// StringEventSender.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.vertx.event;

import strata.foundation.core.action.IActionQueue;
import strata.foundation.kafka.event.KafkaAvroEventSender;

import java.util.Map;

public
class StringEventSender
    extends KafkaAvroEventSender<String>
{

    public
    StringEventSender(
        Map<String,Object> p,
        IActionQueue q,
        String topic)
    {
        super(p,q,String.class,topic);
    }
}

//////////////////////////////////////////////////////////////////////////////
