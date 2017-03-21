// ##########################################################################
// # File Name:	PostalAddressSerializer.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import strata.foundation.value.PostalAddress;

/****************************************************************************
 * 
 */
public 
class PostalAddressSerializer
    extends JsonSerializer<PostalAddress>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<PostalAddress> 
    handledType()
    {
        return PostalAddress.class;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    serialize(
        PostalAddress      value,
        JsonGenerator      generator,
        SerializerProvider serializers) 
            throws IOException,JsonProcessingException
    {
        generator.writeStartObject();
        generator.writeStringField( "Address",value.getAddress() );
        generator.writeStringField( "Street",value.getStreet() );
        generator.writeStringField( "City",value.getCity() );
        generator.writeStringField( "State",value.getState() );
        generator.writeStringField( "CountryCode",value.getCountryCode() );
        generator.writeStringField( "PostalCode",value.getPostalCode() );
        generator.writeEndObject();
    }

}

// ##########################################################################
