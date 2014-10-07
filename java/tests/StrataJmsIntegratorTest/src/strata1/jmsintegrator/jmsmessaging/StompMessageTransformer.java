// ##########################################################################
// # File Name:	StompMessageTransformer.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataJmsIntegratorTest Framework.
// #
// #   			The StrataJmsIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataJmsIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataJmsIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.jmsintegrator.jmsmessaging;

import org.apache.activemq.MessageTransformerSupport;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.stomp.jms.message.StompJmsMapMessage;
import org.fusesource.stomp.jms.util.StompTranslator;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StompMessageTransformer
    extends MessageTransformerSupport
{
    private static final String TRANSFORMATION = "transformation";
    private static final String CONTENT_LENGTH = "content-length";
    
    /************************************************************************
     * Creates a new StompMessageTransformer. 
     *
     */
    public 
    StompMessageTransformer() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Message 
    producerTransform(
        Session         session,
        MessageProducer producer,
        Message         message)
        throws JMSException
    {        
        if ( message instanceof TextMessage )
            message.setStringProperty( TRANSFORMATION,"TEXT" );
        else if ( message instanceof MapMessage )
            message.setStringProperty( TRANSFORMATION,"MAP" );
        else if ( message instanceof ObjectMessage )
            message.setStringProperty( TRANSFORMATION,"OBJECT" );
        
        return message;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Message 
    consumerTransform(
        Session         session,
        MessageConsumer consumer,
        Message         input)
        throws JMSException
    {
        if ( input instanceof BytesMessage )
        {
            Message output = null;
            String  transformation = 
                input.getStringProperty( "transformation" );
            
            System.out.println( "transformation=" + transformation );
            if ( transformation.equalsIgnoreCase( "TEXT" ) )
                output = convertToTextMessage(session,(BytesMessage)input);         
            else if ( transformation.equalsIgnoreCase( "MAP" ) )
                output = convertToMapMessage(session,(BytesMessage)input);
            else if ( transformation.equalsIgnoreCase( "OBJECT" ) )
                output = convertToObjectMessage(session,(BytesMessage)input);
            else
                return input;
            
            super.copyProperties( input,output );
            return output;
        }
        
        return input;
    }

    /************************************************************************
     *  
     *
     * @param input
     * @return
     * @throws JMSException 
     */
    private Message 
    convertToTextMessage(
        Session      session,
        BytesMessage input) 
            throws JMSException
    {
        TextMessage output = session.createTextMessage();
        byte[]      bytes  = new byte[(int)input.getBodyLength()];
        Buffer      buffer = null;
        
        for (int i=0;i<input.getBodyLength();i++)
            bytes[i] = input.readByte();
        
        buffer = new Buffer( bytes );
        output.setText( 
            new String(
                buffer.getData(), 
                buffer.getOffset(), 
                buffer.getLength()));
        super.copyProperties( input,output );
        
        return output;
    }

    /************************************************************************
     *  
     *
     * @param input
     * @return
     * @throws JMSException 
     */
    @SuppressWarnings("unchecked")
    private Message 
    convertToMapMessage(
        Session      session,
        BytesMessage input) 
            throws JMSException
    {
        MapMessage         output = session.createMapMessage();
        byte[]             bytes  = new byte[(int)input.getBodyLength()];
        Buffer             buffer = null;
        Map<String,Object> map = null;
        
        for (int i=0;i<input.getBodyLength();i++)
            bytes[i] = input.readByte();
        
        buffer = new Buffer(bytes);
        map = 
            (Map<String,Object>)
                StompTranslator.readObjectFromBuffer(buffer);
        
        for (String key:map.keySet())
            output.setObject( key,map.get( key ) );
        
        return output;
    }

    /************************************************************************
     *  
     *
     * @param input
     * @return
     * @throws JMSException 
     */
    private Message 
    convertToObjectMessage(
        Session      session,
        BytesMessage input) 
            throws JMSException
    {
        ObjectMessage output = session.createObjectMessage();
        byte[]        bytes  = new byte[(int)input.getBodyLength()];
        Buffer        buffer = null;
        
        for (int i=0;i<input.getBodyLength();i++)
            bytes[i] = input.readByte();
        
        buffer = new Buffer(bytes);
        output.setObject(
            (Serializable)StompTranslator.readObjectFromBuffer(buffer));
        
        return output;
    }

}

// ##########################################################################
