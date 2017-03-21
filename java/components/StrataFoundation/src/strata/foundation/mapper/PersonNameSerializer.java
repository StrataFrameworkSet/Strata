// ##########################################################################
// # File Name:	PersonNameSerializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import strata.foundation.value.PersonName;

/****************************************************************************
 * 
 */
public 
class PersonNameSerializer
    extends JsonSerializer<PersonName>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<PersonName> 
    handledType()
    {
        return PersonName.class;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    serialize(
        PersonName         value,
        JsonGenerator      generator,
        SerializerProvider serializers) 
            throws IOException,JsonProcessingException
    {
        generator.writeStartObject();
        generator.writeStringField( "FirstName",value.getFirstName() );
        
        if (value.hasMiddleName())
            generator.writeStringField( "MiddleName",value.getMiddleName() );
        
        generator.writeStringField( "LastName",value.getLastName() );
        generator.writeEndObject();
    }

}

// ##########################################################################
