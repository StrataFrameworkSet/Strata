// ##########################################################################
// # File Name:	JmsObjectMessage.java
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

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import strata.integration.messaging.DeliveryMode;
import strata.integration.messaging.IObjectMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsObjectMessage
    extends    JmsMessage
    implements IObjectMessage
{
    private final ObjectMessage itsImp;
    
    /************************************************************************
     * Creates a new {@code JmsObjectMessage}. 
     *
     */
    public 
    JmsObjectMessage(ObjectMessage imp)
    {
        itsImp = imp;
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
        try
        {
            itsImp.setObject( payload );
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
    public Object 
    getPayload()
    {
        try
        {
            return itsImp.getObject();
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
    protected ObjectMessage 
    getMessageImp()
    {
        return itsImp;
    }

}

// ##########################################################################
