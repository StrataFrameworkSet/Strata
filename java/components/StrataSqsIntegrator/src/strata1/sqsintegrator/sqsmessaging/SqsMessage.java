// ##########################################################################
// # File Name:	SqsMessage.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegratorTest Framework.
// #
// #   			The StrataSqsIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import strata1.integrator.messaging.DeliveryMode;
import strata1.integrator.messaging.IMessage;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import java.nio.ByteBuffer;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class SqsMessage
    implements IMessage
{
    public static final String CORRELATION_ID = "CorrelationId";
    public static final String RETURN_ADDRESS = "ReturnAddress";
    public static final String DELIVERY_MODE  = "DeliveryMode";
    public static final String TIME_TO_LIVE   = "TimeToLive";
    public static final String PAYLOAD_TYPE   = "PayloadType";
    
    private final Message itsImp;
    
    /************************************************************************
     * Creates a new SqsMessage. 
     *
     */
    protected 
    SqsMessage()
    {
        itsImp = new Message();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setMessageId(String messageId)
    {
        itsImp.setMessageId( messageId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setCorrelationId(String correlationId)
    {
        itsImp.addMessageAttributesEntry( 
            CORRELATION_ID,
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( correlationId ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setReturnAddress(String returnAddress)
    {
        itsImp.addMessageAttributesEntry( 
            RETURN_ADDRESS,
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( returnAddress ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        itsImp.addMessageAttributesEntry( 
            RETURN_ADDRESS,
            new MessageAttributeValue()
                .withDataType( "String.DeliveryMode" )
                .withStringValue( mode.name() ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setTimeToLive(long timeToLive)
    {
        return setLongProperty(TIME_TO_LIVE,timeToLive);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setByteProperty(String name,byte value)
    {        
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.byte" )
                .withStringValue( new Byte( value ).toString() ) );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setBooleanProperty(String name,boolean value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "String.Boolean" )
                .withStringValue( value ? "true" : "false" ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setShortProperty(String name,short value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.short" )
                .withStringValue( new Short(value).toString() ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setIntProperty(String name,int value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.int" )
                .withStringValue( new Integer(value).toString() ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setLongProperty(String name,long value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.long" )
                .withStringValue( new Long(value).toString() ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setFloatProperty(String name,float value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.float" )
                .withStringValue( new Float(value).toString() ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setDoubleProperty(String name,double value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "Number.double" )
                .withStringValue( new Double(value).toString() ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setStringProperty(String name,String value)
    {
        itsImp.addMessageAttributesEntry( 
            name,
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( value ) );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public String 
    getMessageId()
    {
        return itsImp.getMessageId();
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public String 
    getCorrelationId()
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( CORRELATION_ID ) )
            return null;
        
        return 
            properties
                .get( CORRELATION_ID )
                .getStringValue();
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public String 
    getReturnAddress()
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( RETURN_ADDRESS ) )
            return null;
        
        return 
            properties
                .get( RETURN_ADDRESS )
                .getStringValue();
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public DeliveryMode 
    getDeliveryMode()
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( DELIVERY_MODE ) )
            return null;
        
        return 
            DeliveryMode.valueOf(
                properties
                    .get( DELIVERY_MODE )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public long 
    getTimeToLive()
    {
        return getLongProperty( TIME_TO_LIVE );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public byte 
    getByteProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Byte(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public boolean 
    getBooleanProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Boolean(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public short 
    getShortProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Short(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public int 
    getIntegerProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Integer(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public long 
    getLongProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Long(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public float 
    getFloatProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Float(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public double 
    getDoubleProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            new Double(
                properties
                    .get( name )
                    .getStringValue() );
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public String 
    getStringProperty(String name)
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( name ) )
            throw new IllegalArgumentException("no such property:" + name);
        
        return 
            properties
                .get( name )
                .getStringValue();
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public boolean 
    hasProperty(String name)
    {
        return 
            itsImp
                .getMessageAttributes()
                .containsKey( name );
    }

    /************************************************************************
     *  
     *
     * @param payloadType
     * @return
     */
    protected IMessage
    setPayloadType(PayloadType payloadType)
    {
        itsImp.addMessageAttributesEntry( 
            PAYLOAD_TYPE,
            new MessageAttributeValue()
                .withDataType( "String.PayloadType" )
                .withStringValue( payloadType.name() ) );
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected PayloadType
    getPayloadType()
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( PAYLOAD_TYPE ) )
            return PayloadType.STRING;
        
        return 
            PayloadType.valueOf(
                properties
                    .get( DELIVERY_MODE )
                    .getStringValue() );
        
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected Message
    getMessageImp()
    {
        return itsImp;
    }
}

// ##########################################################################
