// ##########################################################################
// # File Name:	PhoneNumberDeserializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import strata.foundation.value.PhoneNumber;

/****************************************************************************
 * 
 */
public 
class PhoneNumberDeserializer
    extends JsonDeserializer<PhoneNumber>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PhoneNumber 
    deserialize(
        JsonParser             parser,
        DeserializationContext context) 
            throws IOException,JsonProcessingException
    {
        JsonNode node  = parser.getCodec().readTree(parser);
        String   value = node.asText();
        
        return new PhoneNumber(value);
    }

}

// ##########################################################################
