// ##########################################################################
// # File Name:	SqsObjectMessage.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import strata1.integrator.messaging.DeliveryMode;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IObjectMessage;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsObjectMessage
    extends SqsMessage
    implements IObjectMessage
{

    /************************************************************************
     * Creates a new SqsObjectMessage. 
     *
     */
    public 
    SqsObjectMessage(ISqsMessagingSession session)
    {
        super( session );
        setPayloadType( PayloadType.OBJECT );
    }

    /************************************************************************
     * Creates a new SqsObjectMessage. 
     *
     * @param imp
     */
    public 
    SqsObjectMessage(ISqsMessagingSession session,Message imp)
    {
        super( session,imp );
        setPayloadType( PayloadType.OBJECT );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setMessageId(String messageId)
    {
        super.setMessageId( messageId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setCorrelationId(String correlationId)
    {
        super.setCorrelationId( correlationId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setSequenceNum(long sequenceNum)
    {
        super.setSequenceNum( sequenceNum );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setReturnAddress(String returnAddress)
    {
        super.setReturnAddress( returnAddress );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        super.setDeliveryMode( mode );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setByteProperty(String name,byte value)
    {
        super.setByteProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setBooleanProperty(String name,boolean value)
    {
        super.setBooleanProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setShortProperty(String name,short value)
    {
        super.setShortProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setIntProperty(String name,int value)
    {
        super.setIntProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setLongProperty(String name,long value)
    {
        super.setLongProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setFloatProperty(String name,float value)
    {
        super.setFloatProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setDoubleProperty(String name,double value)
    {
        super.setDoubleProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setStringProperty(String name,String value)
    {
        super.setStringProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IObjectMessage 
    setPayload(Serializable payload)
    {
        ByteArrayOutputStream bytes  = new ByteArrayOutputStream();
        ObjectOutput          output = null;;
        try
        {
            output = new ObjectOutputStream( bytes );
            output.writeObject( payload );
            getMessageImp()
                .setBody( new String(Base64.encode(bytes.toByteArray())) );
        }
        catch(IOException e)
        {
            throw new IllegalStateException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    getPayload()
    {
        ByteArrayInputStream bytes  = null;
        ObjectInput          input = null;
        
        if ( getMessageImp().getBody() == null )
            return null;
        
        bytes = 
            new ByteArrayInputStream(
                Base64.decode( getMessageImp().getBody() ) );
        
        try
        {
            input = new ObjectInputStream( bytes );
            return input.readObject();
        }
        catch(Exception e)
        {
            throw new IllegalStateException( e );
        }
    }
}

// ##########################################################################
