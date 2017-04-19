// ##########################################################################
// # File Name:	PostalAddressDeserializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import strata.foundation.value.PostalAddress;
import strata.foundation.value.PostalAddressBuilder;

/****************************************************************************
 * 
 */
public 
class PostalAddressDeserializer
    extends JsonDeserializer<PostalAddress>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PostalAddress 
    deserialize(
        JsonParser             parser,
        DeserializationContext context) 
            throws IOException,JsonProcessingException
    {
        JsonNode node = parser.getCodec().readTree(parser);
        
        return
            new PostalAddressBuilder()
                .setAddress( node.get( "Address" ).asText() )
                .setStreet( node.get( "Street" ).asText() )
                .setCity( node.get("City").asText() )
                .setState( node.get( "State" ).asText() )
                .setCountryCode( node.get("CountryCode").asText() )
                .setPostalCode( node.get("PostalCode").asText() )
                .toPostalAddress();        
    }

}

// ##########################################################################
