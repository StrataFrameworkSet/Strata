// ##########################################################################
// # File Name:	PersonNameDeserializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import strata.foundation.value.PersonName;

/****************************************************************************
 * 
 */
public 
class PersonNameDeserializer
    extends JsonDeserializer<PersonName>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PersonName 
    deserialize(
        JsonParser             parser,
        DeserializationContext context) 
            throws IOException,JsonProcessingException
    {
        JsonNode node   = parser.getCodec().readTree(parser);
        String   first  = node.get( "FirstName" ).asText();
        String   middle = null;
        String   last   = node.get( "LastName" ).asText();
        
        if ( node.has( "MiddleName" ) )
            middle = node.get( "MiddleName" ).asText();
        
        return new PersonName( first,middle,last );        
    }

}

// ##########################################################################
