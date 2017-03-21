// ##########################################################################
// # File Name:	JsonObjectMapper.java
// # Copyright:	2016, SomethingPay, LLC. All Rights Reserved.
// ##########################################################################

package strata.foundation.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import strata.foundation.value.EmailAddress;
import strata.foundation.value.PersonName;
import strata.foundation.value.PhoneNumber;
import strata.foundation.value.PostalAddress;

/****************************************************************************
 * 
 */
public 
class JsonObjectMapper<T>
    implements IObjectMapper<T,String>
{
    
    private final ObjectMapper       itsMapper;
    private final Map<String,String> itsTypeMappings;
    
    public
    JsonObjectMapper()
    {
        SimpleModule module = new SimpleModule();
        
        module
            .addSerializer( new EmailAddressSerializer() )
            .addDeserializer( 
                EmailAddress.class,
                new EmailAddressDeserializer() )
            .addSerializer( new PhoneNumberSerializer() )
            .addDeserializer( 
                PhoneNumber.class,
                new PhoneNumberDeserializer() )
            .addSerializer( new PostalAddressSerializer() )
            .addDeserializer( 
                PostalAddress.class,
                new PostalAddressDeserializer() )
            .addSerializer( new PersonNameSerializer() )
            .addDeserializer( 
                PersonName.class,
                new PersonNameDeserializer() );
        
        itsMapper = new ObjectMapper();
        itsMapper
            .setPropertyNamingStrategy( 
                new PropertyNamingStrategy.UpperCamelCaseStrategy() )
            .enable( MapperFeature.REQUIRE_SETTERS_FOR_GETTERS )
            .enable( MapperFeature.ALLOW_EXPLICIT_PROPERTY_RENAMING )
            .enable( MapperFeature.SORT_PROPERTIES_ALPHABETICALLY )
            .enable( DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES )
            .enable( SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS )
            .registerModule( module );
        itsTypeMappings = new HashMap<String,String>();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <S extends T> String 
    toPayload(S object)
    {
        try
        {
            return itsMapper.writeValueAsString( object );
        }
        catch(JsonProcessingException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <S extends T> S 
    toObject(Class<S> type,String payload)
    {
        try
        {
            return itsMapper.readValue( preprocess(payload),type );
        }
        catch(IOException  e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @param sourceType
     * @param destType
     * @return
     */
    public JsonObjectMapper<T>
    insertMapping(String sourceType,Class<?> destType)
    {
        itsTypeMappings.put( sourceType,destType.getName() );
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public JsonObjectMapper<T>
    clearMappings()
    {
        itsTypeMappings.clear();
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param payload
     * @return
     */
    private String
    preprocess(String payload)
    {
        String output = null;
        
        output = payload.replace( "__type","@class" );
        output = output.replace( "$type","@class" );
        
        for (Entry<String,String> mapping:itsTypeMappings.entrySet())
            output = output.replace( mapping.getKey(),mapping.getValue() );
        
        return output;
    }
}

// ##########################################################################
