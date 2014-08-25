// ##########################################################################
// # File Name:	JmsQueueMessagingSession.java
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

package strata1.jmsintegrator.jmsmessaging;

import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessageReceiver;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.IObjectMessage;
import strata1.integrator.messaging.IStringMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsQueueMessagingSession
    implements IMessagingSession
{
    private final QueueConnectionFactory factory;
    private QueueConnection              connection;
    private Session                      session;
    private boolean                      receiving;
    private boolean                      closed;
    private int                          acknowledgmentMode;
    private int                          retryAttempts;
    private int                          retryDelay;
    private int                          retryTimeout;
    
    /************************************************************************
     * Creates a new JmsQueueMessagingSession. 
     *
     */
    public 
    JmsQueueMessagingSession(QueueConnectionFactory f,int ackMode)
    {
        factory            = f;
        acknowledgmentMode = ackMode;
        connect();
    }

    /************************************************************************
     * Creates a new JmsQueueMessagingSession. 
     *
     */
    public 
    JmsQueueMessagingSession(QueueConnectionFactory f)
    {
        this( f,Session.DUPS_OK_ACKNOWLEDGE );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageSender 
    createMessageSender(String id)
    {
        try
        {
            return createProducer( id );   
        }
        catch (JMSException e1)
        {
            reconnect();

            try
            {
                return createProducer( id );  
            }
            catch (JMSException e2)
            {
                throw new IllegalStateException( e2 );
            }
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id)
    {
        try
        {
            return createConsumer( id );   
        }
        catch (JMSException e1)
        {
            reconnect();

            try
            {
                return createConsumer( id );  
            }
            catch (JMSException e2)
            {
                throw new IllegalStateException( e2 );
            }
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessageReceiver 
    createMessageReceiver(String id,String selector)
    {
        try
        {
            return createConsumer( id,selector );   
        }
        catch (JMSException e1)
        {
            reconnect();

            try
            {
                return createConsumer( id,selector );  
            }
            catch (JMSException e2)
            {
                throw new IllegalStateException( e2 );
            }
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    createStringMessage()
    {
        try
        {
            return new JmsStringMessage(session.createTextMessage());
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
    public IMapMessage 
    createMapMessage()
    {
        try
        {
            return new JmsMapMessage(session.createMapMessage());
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
    public IObjectMessage 
    createObjectMessage()
    {
        try
        {
            return new JmsObjectMessage(session.createObjectMessage());
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
    startReceiving()
    {
        try
        {
            connection.start();
            receiving = true;           
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
    stopReceiving()
    {
        try
        {
            connection.stop();
            receiving = false;          
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
    close()
    {
        try
        {
            session.close();
            connection.close();
            closed = true;
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
    isReceiving()
    {
        return receiving;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isClosed()
    {
        return closed;
    }

    /************************************************************************
     *  
     *
     * @param retries
     * @return
     */
    public JmsQueueMessagingSession
    setRetryAttempts(int retries)
    {
        retryAttempts = retries;
        return this;
    }

    /************************************************************************
     *  
     *
     * @param delay
     * @return
     */
    public JmsQueueMessagingSession
    setRetryDelay(int delay)
    {
        retryDelay = delay;
        return this;
    }

    /************************************************************************
     *  
     *
     * @param timeout
     * @return
     */
    public JmsQueueMessagingSession
    setRetryTimeout(int timeout)
    {
        retryTimeout = timeout;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public int
    getRetryAttempts()
    {
        return retryAttempts;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public int
    getRetryDelay()
    {
        return retryDelay;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public int
    getRetryTimeout()
    {
        return retryTimeout;
    }
    
    /************************************************************************
     *  
     *
     */
    private void
    connect()
    {
        try
        {
            createConnectionAndSession();
        }
        catch (JMSException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     *  
     *
     */
    private void
    reconnect()
    {    
        try
        {
            session.close();
        }
        catch (JMSException e) {}
    
        try
        {
            connection.close();
        }
        catch (JMSException e) {}
    
        for (int i=0;i<getRetryAttempts();i++)
        {     
            try
            {
                createConnectionAndSession();
                return;
            }
            catch (JMSException e)
            {
                try
                {
                    Thread.sleep( getRetryDelay() );
                }
                catch (InterruptedException e1) {}
            }
        }
    
        throw new IllegalStateException( "Reconnect failed." );
        
    }

    /************************************************************************
     *  
     *
     * @throws JMSException
     */
    private void 
    createConnectionAndSession() throws JMSException
    {
        connection = factory.createQueueConnection();
        session = connection.createQueueSession(false,acknowledgmentMode);
        receiving = false;
        closed    = false;
    }

    /************************************************************************
     *  
     *
     * @param id
     * @return
     * @throws JMSException
     */
    private IMessageSender 
    createProducer(String id) throws JMSException
    {
        return
            new JmsMessageSender(
                this,
                session.createProducer(createQueue(id)));
    }

    /************************************************************************
     *  
     *
     * @param id
     * @return
     * @throws JMSException
     */
    private IMessageReceiver 
    createConsumer(String id) throws JMSException
    {
        return
            new JmsMessageReceiver(
                this,
                session.createConsumer(createQueue(id)));
    }

    /************************************************************************
     *  
     *
     * @param id
     * @return
     * @throws JMSException
     */
    private IMessageReceiver 
    createConsumer(String id,String selector) throws JMSException
    {
        return
            new JmsMessageReceiver(
                this,
                session.createConsumer(createQueue(id),selector));
    }

    /************************************************************************
     *  
     *
     * @param queueName
     * @return
     * @throws JMSException
     */
    private Queue
    createQueue(String queueName) 
        throws JMSException
    {
        return session.createQueue( queueName );
    }
}

// ##########################################################################
