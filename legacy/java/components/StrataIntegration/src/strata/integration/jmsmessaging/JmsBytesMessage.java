// ##########################################################################
// # File Name:	JmsBytesMessage.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.jmsmessaging;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import strata.integration.messaging.DeliveryMode;
import strata.integration.messaging.IBytesMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsBytesMessage
    extends    JmsMessage
    implements IBytesMessage
{
    private final BytesMessage itsImp;
    
    /************************************************************************
     * Creates a new {@code JmsBytesMessage}. 
     *
     */
    public 
    JmsBytesMessage(BytesMessage imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setMessageId(String messageId)
    {
        super.setMessageId( messageId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setCorrelationId(String correlationId)
    {
        super.setCorrelationId( correlationId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setSequenceNum(long sequenceNum)
    {
        super.setSequenceNum( sequenceNum );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setReturnAddress(String returnAddress)
    {
        super.setReturnAddress( returnAddress );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        super.setDeliveryMode( mode );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setByteProperty(String name,byte value)
    {
        super.setByteProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setBooleanProperty(String name,boolean value)
    {
        super.setBooleanProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setShortProperty(String name,short value)
    {
        super.setShortProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setIntProperty(String name,int value)
    {
        super.setIntProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setLongProperty(String name,long value)
    {
        super.setLongProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setFloatProperty(String name,float value)
    {
        super.setFloatProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setDoubleProperty(String name,double value)
    {
        super.setDoubleProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setStringProperty(String name,String value)
    {
        super.setStringProperty( name,value );
        return this;
    }


    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBytesMessage 
    setPayload(byte[] payload)
    {
        try
        {
            itsImp.writeBytes( payload );
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
    public byte[] 
    getPayload()
    {
        try
        {
            byte[] payload = new byte[(int)itsImp.getBodyLength()];
            
            itsImp.readBytes( payload );
            return payload;
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected BytesMessage 
    getMessageImp()
    {
        return itsImp;
    }

}

// ##########################################################################
