// ##########################################################################
// # File Name:	EmailAddressDeserializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import strata.foundation.value.EmailAddress;

/****************************************************************************
 * 
 */
public 
class EmailAddressDeserializer
    extends JsonDeserializer<EmailAddress>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public EmailAddress 
    deserialize(
        JsonParser             parser,
        DeserializationContext context) 
            throws IOException,JsonProcessingException
    {
        JsonNode node  = parser.getCodec().readTree(parser);
        String   value = node.asText();
        
        return new EmailAddress(value);
    }

}

// ##########################################################################
