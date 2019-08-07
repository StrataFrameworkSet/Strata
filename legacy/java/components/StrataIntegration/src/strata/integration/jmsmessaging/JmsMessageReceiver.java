// ##########################################################################
// # File Name:	JmsMessageReceiver.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataJmsIntegrator Framework.
// #
// #   			The StrataJmsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataJmsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataJmsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.jmsmessaging;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.IMessageListener;
import strata.integration.messaging.IMessageReceiver;
import strata.integration.messaging.IMessagingSession;
import strata.integration.messaging.MixedModeException;
import strata.integration.messaging.NoMessageReceivedException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMessageReceiver
    implements IMessageReceiver
{
    private final IMessagingSession itsSession;
    private final MessageConsumer   itsImp;
    private IMessageListener        itsListener;
    
    /************************************************************************
     * Creates a new JmsMessageReceiver. 
     *
     */
    public 
    JmsMessageReceiver(IMessagingSession session,MessageConsumer imp)
    {
        itsSession  = session;
        itsImp      = imp;
        itsListener = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    setListener(IMessageListener listener)
    {
        itsListener = listener;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessagingSession 
    getSession()
    {
        return itsSession;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageListener 
    getListener()
    {
        return itsListener;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getSelector()
    {
        try
        {
            return itsImp.getMessageSelector();
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startListening()
    {
        try
        {
            itsImp.setMessageListener( new JmsMessageListener(itsListener) );
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }       
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopListening()
    {
        try
        {
            itsImp.setMessageListener( null );
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isListening()
    {
        try
        {
            return itsImp.getMessageListener() != null;
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive() 
        throws MixedModeException
    {
        try
        {
            return wrapMessage( itsImp.receive() );
        }
        catch (JMSException e)
        {
            throw new MixedModeException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receive(long timeOutInMs) 
        throws MixedModeException,NoMessageReceivedException
    {
        try
        {
            Message message = itsImp.receive(timeOutInMs);
            
            if ( message == null )
                throw new NoMessageReceivedException();
            
            return wrapMessage( message );
        }
        catch (JMSException e)
        {
            throw new MixedModeException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    receiveNoWait() 
        throws MixedModeException,NoMessageReceivedException
    {
        try
        {
            Message message = itsImp.receiveNoWait();
            
            if ( message == null )
                throw new NoMessageReceivedException();
            
            return wrapMessage( message );
        }
        catch (JMSException e)
        {
            throw new MixedModeException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
        try
        {
            itsImp.close();
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @param message
     * @return
     * @throws NoMessageReceivedException
     */
    private IMessage 
    wrapMessage(Message message)
    {
        if ( message instanceof TextMessage )
            return new JmsStringMessage((TextMessage)message);
        else if ( message instanceof MapMessage )
            return new JmsMapMessage((MapMessage)message);
        else if ( message instanceof ObjectMessage )
            return new JmsObjectMessage((ObjectMessage)message);
        else if ( message instanceof BytesMessage )
            return new JmsBytesMessage((BytesMessage)message);
        
        return null; // should never reach this point
    }

}

// ##########################################################################
