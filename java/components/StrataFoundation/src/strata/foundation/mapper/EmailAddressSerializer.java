// ##########################################################################
// # File Name:	EmailAddressSerializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import strata.foundation.value.EmailAddress;

/****************************************************************************
 * 
 */
public 
class EmailAddressSerializer
    extends JsonSerializer<EmailAddress>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<EmailAddress> 
    handledType()
    {
        return EmailAddress.class;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    serialize(
        EmailAddress       value,
        JsonGenerator      generator,
        SerializerProvider serializers) 
            throws IOException,JsonProcessingException
    {
        generator.writeString( value.toString() );
    }

}

// ##########################################################################
