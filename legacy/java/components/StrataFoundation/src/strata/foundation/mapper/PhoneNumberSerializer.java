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
import strata.foundation.value.PhoneNumber;

/****************************************************************************
 * 
 */
public 
class PhoneNumberSerializer
    extends JsonSerializer<PhoneNumber>
{

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<PhoneNumber> 
    handledType()
    {
        return PhoneNumber.class;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    serialize(
        PhoneNumber        value,
        JsonGenerator      generator,
        SerializerProvider serializers) 
            throws IOException,JsonProcessingException
    {
        generator.writeString( value.toString() );
    }

}

// ##########################################################################
