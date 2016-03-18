// ##########################################################################
// # File Name:	JmsMessage.java
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

import javax.jms.JMSException;
import javax.jms.Message;
import strata1.integrator.messaging.DeliveryMode;
import strata1.integrator.messaging.IMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class JmsMessage
    implements IMessage
{
    private static final String RETURN_ADDRESS_PROPERTY = "ReturnAddress";
    
    /************************************************************************
     * Creates a new JmsMessage. 
     *
     */
    protected 
    JmsMessage() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setMessageId(String messageId)
    {
        try
        {
            getMessageImp().setJMSMessageID( messageId );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setCorrelationId(String correlationId)
    {
        try
        {
            getMessageImp().setJMSCorrelationID( correlationId );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setReturnAddress(String returnAddress)
    {
        try
        {
            getMessageImp()
                .setStringProperty( RETURN_ADDRESS_PROPERTY,returnAddress );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        try
        {
            getMessageImp()
                .setJMSDeliveryMode(
                    mode == DeliveryMode.PERSISTENT 
                        ? javax.jms.DeliveryMode.PERSISTENT 
                        : javax.jms.DeliveryMode.NON_PERSISTENT );
            
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setByteProperty(String name,byte value)
    {
        try
        {
            getMessageImp().setByteProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setBooleanProperty(String name,boolean value)
    {
        try
        {
            getMessageImp().setBooleanProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setShortProperty(String name,short value)
    {
        try
        {
            getMessageImp().setShortProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setIntProperty(String name,int value)
    {
        try
        {
            getMessageImp().setIntProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setLongProperty(String name,long value)
    {
        try
        {
            getMessageImp().setLongProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setFloatProperty(String name,float value)
    {
        try
        {
            getMessageImp().setFloatProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDoubleProperty(String name,double value)
    {
        try
        {
            getMessageImp().setDoubleProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setStringProperty(String name,String value)
    {
        try
        {
            getMessageImp().setStringProperty( name,value );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getMessageId()
    {
        try
        {
            return getMessageImp().getJMSMessageID();
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
    public String 
    getCorrelationId()
    {
        try
        {
            return getMessageImp().getJMSCorrelationID();
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
    public String 
    getReturnAddress()
    {
        try
        {
            return 
                getMessageImp()
                    .getStringProperty( RETURN_ADDRESS_PROPERTY );
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
    public DeliveryMode 
    getDeliveryMode()
    {
        try
        {
            return 
                getMessageImp().getJMSDeliveryMode() == 
                    javax.jms.DeliveryMode.PERSISTENT
                    ? DeliveryMode.PERSISTENT
                    : DeliveryMode.NON_PERSISTENT;
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
    public byte 
    getByteProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    getBooleanProperty(String name)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public short 
    getShortProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getIntProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getLongProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public float 
    getFloatProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public double 
    getDoubleProperty(String name)
    {
        return 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getStringProperty(String name)
    {
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasProperty(String name)
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    acknowledge()
    {
        try
        {
            getMessageImp().acknowledge();
        }
        catch(JMSException e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected abstract Message
    getMessageImp();
}

// ##########################################################################
