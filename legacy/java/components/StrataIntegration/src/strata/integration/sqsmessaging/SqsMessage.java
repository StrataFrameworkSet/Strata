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

package strata.integration.sqsmessaging;

import java.util.Map;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import strata.integration.messaging.DeliveryMode;
import strata.integration.messaging.IMessage;

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
    public static final String SEQUENCE_NUM   = "SequenceNum";
    public static final String RETURN_ADDRESS = "ReturnAddress";
    public static final String DELIVERY_MODE  = "DeliveryMode";
    public static final String PAYLOAD_TYPE   = "PayloadType";
    
    private final ISqsMessagingSession itsSession;
    private final Message              itsImp;
    private String                     itsQueueUrl;

    
    /************************************************************************
     * Creates a new SqsMessage. 
     *
     */
    protected 
    SqsMessage(ISqsMessagingSession session)
    {
        this( session,new Message() );
    }
    
    /************************************************************************
     * Creates a new SqsMessage. 
     *
     * @param imp
     */
    protected 
    SqsMessage(ISqsMessagingSession session,Message imp)
    {
        itsSession  = session;
        itsImp      = imp;
        itsQueueUrl = null;
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
        MessageAttributeValue value = 
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( correlationId );
        
        if ( hasProperty( CORRELATION_ID ) )
            itsImp
                .getMessageAttributes()
                .put( CORRELATION_ID,value );
        else
            itsImp.addMessageAttributesEntry( CORRELATION_ID,value );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setSequenceNum(long sequenceNum)
    {
        MessageAttributeValue value = 
            new MessageAttributeValue()
                .withDataType( "Number.long" )
                .withStringValue( new Long(sequenceNum).toString() );
        
        if ( hasProperty( SEQUENCE_NUM ) )
            itsImp
                .getMessageAttributes()
                .put( SEQUENCE_NUM,value );
        else
            itsImp.addMessageAttributesEntry( SEQUENCE_NUM,value );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setReturnAddress(String returnAddress)
    {
        MessageAttributeValue value = 
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( returnAddress );
        
        if ( hasProperty( RETURN_ADDRESS ) )
            itsImp
                .getMessageAttributes()
                .put( RETURN_ADDRESS,value );
        else
            itsImp.addMessageAttributesEntry( RETURN_ADDRESS,value );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        MessageAttributeValue value = 
            new MessageAttributeValue()
                .withDataType( "String.DeliveryMode" )
                .withStringValue( mode.name() );
        
        if ( hasProperty( DELIVERY_MODE ) )
            itsImp
                .getMessageAttributes()
                .put( DELIVERY_MODE,value );
        else
            itsImp.addMessageAttributesEntry( DELIVERY_MODE,value );
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setByteProperty(String name,byte value)
    {     
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "Number.byte" )
                .withStringValue( new Byte( value ).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setBooleanProperty(String name,boolean value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "String.Boolean" )
                .withStringValue( value ? "true" : "false" );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setShortProperty(String name,short value)
    {
        MessageAttributeValue attribute =            
             new MessageAttributeValue()
                .withDataType( "Number.short" )
                .withStringValue( new Short(value).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setIntProperty(String name,int value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "Number.int" )
                .withStringValue( new Integer(value).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setLongProperty(String name,long value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "Number.long" )
                .withStringValue( new Long(value).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setFloatProperty(String name,float value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "Number.float" )
                .withStringValue( new Float(value).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setDoubleProperty(String name,double value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "Number.double" )
                .withStringValue( new Double(value).toString() );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

        return this;
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    public IMessage 
    setStringProperty(String name,String value)
    {
        MessageAttributeValue attribute = 
            new MessageAttributeValue()
                .withDataType( "String" )
                .withStringValue( value );
        
        if ( hasProperty( name ) )
            itsImp
                .getMessageAttributes()
                .put( name,attribute );
        else
            itsImp.addMessageAttributesEntry( name,attribute );

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
    public long 
    getSequenceNum()
    {
        Map<String,MessageAttributeValue> properties = 
            itsImp.getMessageAttributes();
        
        if ( !properties.containsKey( SEQUENCE_NUM ) )
            return 0L;
        
        return 
            new Long(
                properties
                    .get( SEQUENCE_NUM )
                    .getStringValue());
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
    getIntProperty(String name)
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
     * {@inheritDoc} 
     */
    @Override
    public void 
    acknowledge()
    {
        itsSession.acknowledge(this);
    }

    /************************************************************************
     *  
     *
     * @param queueUrl
     * @return
     */
    public SqsMessage
    setQueueUrl(String queueUrl)
    {
        itsQueueUrl = queueUrl;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getQueueUrl()
    {
        return itsQueueUrl;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasQueueUrl()
    {
        return itsQueueUrl != null && !itsQueueUrl.isEmpty();
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
        MessageAttributeValue value = 
            new MessageAttributeValue()
                .withDataType( "String.PayloadType" )
                .withStringValue( payloadType.name() );
        
        if ( hasProperty( PAYLOAD_TYPE ) )
            itsImp
                .getMessageAttributes()
                .put( PAYLOAD_TYPE,value );
        else
            itsImp.addMessageAttributesEntry( PAYLOAD_TYPE,value );
        
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
