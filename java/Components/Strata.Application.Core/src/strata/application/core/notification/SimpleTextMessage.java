//////////////////////////////////////////////////////////////////////////////
// SimpleTextMessage.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.notification;

import strata.foundation.core.value.PhoneNumber;

import java.util.List;

public
class SimpleTextMessage
    extends AbstractTextMessage
{
    private final String itsContent;

    public
    SimpleTextMessage(List<PhoneNumber> recipients,String content)
    {
        super(recipients);
        itsContent = content;
    }

    @Override
    public String
    getContent()
    {
        return itsContent;
    }
}

//////////////////////////////////////////////////////////////////////////////
