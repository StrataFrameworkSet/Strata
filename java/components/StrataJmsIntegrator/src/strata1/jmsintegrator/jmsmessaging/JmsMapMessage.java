// ##########################################################################
// # File Name:	JmsMapMessage.java
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

package strata1.jmsintegrator.jmsmessaging;

import strata1.integrator.messaging.DeliveryMode;
import strata1.integrator.messaging.IBytesMessage;
import strata1.integrator.messaging.IMapMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class JmsMapMessage
    extends    JmsMessage
    implements IMapMessage
{
    private final MapMessage itsImp;
    
    /************************************************************************
     * Creates a new {@code JmsMapMessage}. 
     *
     */
    public 
    JmsMapMessage(MapMessage imp)
    {
        itsImp = imp;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setMessageId(String messageId)
    {
        super.setMessageId( messageId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setCorrelationId(String correlationId)
    {
        super.setCorrelationId( correlationId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setSequenceNum(long sequenceNum)
    {
        super.setSequenceNum( sequenceNum );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setReturnAddress(String returnAddress)
    {
        super.setReturnAddress( returnAddress );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        super.setDeliveryMode( mode );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setByteProperty(String name,byte value)
    {
        super.setByteProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setBooleanProperty(String name,boolean value)
    {
        super.setBooleanProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setShortProperty(String name,short value)
    {
        super.setShortProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setIntProperty(String name,int value)
    {
        super.setIntProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setLongProperty(String name,long value)
    {
        super.setLongProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setFloatProperty(String name,float value)
    {
        super.setFloatProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setDoubleProperty(String name,double value)
    {
        super.setDoubleProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setStringProperty(String name,String value)
    {
        super.setStringProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setByte(String key,byte item)
    {
        try
        {
            itsImp.setByte( key,item );
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
    public IMapMessage 
    setBytes(String key,byte[] item)
    {
        try
        {
            itsImp.setBytes( key,item );
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
    public IMapMessage 
    setBoolean(String key,boolean item)
    {
        try
        {
            itsImp.setBoolean( key,item );
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
    public IMapMessage 
    setChar(String key,char item)
    {
        try
        {
            itsImp.setChar( key,item );
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
    public IMapMessage 
    setShort(String key,short item)
    {
        try
        {
            itsImp.setShort( key,item );
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
    public IMapMessage 
    setInt(String key,int item)
    {
        try
        {
            itsImp.setInt( key,item );
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
    public IMapMessage 
    setLong(String key,long item)
    {
        try
        {
            itsImp.setLong( key,item );
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
    public IMapMessage 
    setFloat(String key,float item)
    {
        try
        {
            itsImp.setFloat( key,item );
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
    public IMapMessage 
    setDouble(String key,double item)
    {
        try
        {
            itsImp.setDouble( key,item );
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
    public IMapMessage 
    setString(String key,String item)
    {
        try
        {
            itsImp.setString( key,item );
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
    public IMapMessage 
    setObject(String key,Object item)
    {
        try
        {
            itsImp.setObject( key,item );
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
    public byte 
    getByte(String key)
    {
        try
        {
            return itsImp.getByte( key );
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
    public byte[] 
    getBytes(String key)
    {
        try
        {
            return itsImp.getBytes( key );
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
    public boolean 
    getBoolean(String key)
    {
        try
        {
            return itsImp.getBoolean( key );
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
    public char 
    getChar(String key)
    {
        try
        {
            return itsImp.getChar( key );
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
    public short 
    getShort(String key)
    {
        try
        {
            return itsImp.getShort( key );
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
    public int 
    getInt(String key)
    {
        try
        {
            return itsImp.getInt( key );
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
    public long 
    getLong(String key)
    {
        try
        {
            return itsImp.getLong( key );
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
    public float 
    getFloat(String key)
    {
        try
        {
            return itsImp.getFloat( key );
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
    public double 
    getDouble(String key)
    {
        try
        {
            return itsImp.getDouble( key );
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
    public String 
    getString(String key)
    {
        try
        {
            return itsImp.getString( key );
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
    public Object 
    getObject(String key)
    {
        try
        {
            return itsImp.getObject( key );
        }
        catch (JMSException e)
        {
            throw new IllegalArgumentException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<String> 
    getItemKeys()
    {
        try
        {
            return 
                new HashSet<String>(
                    Collections.list( itsImp.getMapNames() ) );

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
    public boolean 
    hasItem(String key)
    {
        try
        {
            return itsImp.itemExists( key );
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
    protected MapMessage 
    getMessageImp()
    {
        return itsImp;
    }

}

// ##########################################################################
