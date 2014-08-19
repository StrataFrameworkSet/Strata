// ##########################################################################
// # File Name:	InMemoryMapMessage.java
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

package strata1.integrator.inmemorymessaging;

import strata1.integrator.messaging.DeliveryMode;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.IMessage;
import java.util.Collection;
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
class InMemoryMapMessage
    extends    InMemoryMessage
    implements IMapMessage
{
    private Map<String,Object> itsPayload;
    
    /************************************************************************
     * Creates a new {@code InMemoryMapMessage}. 
     *
     */
    public 
    InMemoryMapMessage()
    {
        itsPayload = new HashMap<String,Object>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setMessageId(String mId)
    {
        return (IMapMessage)super.setMessageId( mId );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setCorrelationId(String cId)
    {
        return (IMapMessage)super.setCorrelationId( cId );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setReturnAddress(String retAddr)
    {
        return (IMapMessage)super.setReturnAddress( retAddr );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        return (IMapMessage)super.setDeliveryMode( mode );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setByte(String key,byte item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setBytes(String key,byte[] item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setBoolean(String key,boolean item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setChar(String key,char item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setShort(String key,short item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setInt(String key,int item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setLong(String key,long item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setFloat(String key,float item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setDouble(String key,double item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setString(String key,String item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setObject(String key,Object item)
    {
        itsPayload.put( key,item );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public byte 
    getByte(String key)
    {
        return (Byte)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public byte[] 
    getBytes(String key)
    {
        return (byte[])itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    getBoolean(String key)
    {
        return (Boolean)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public char 
    getChar(String key)
    {
        return (Character)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public short 
    getShort(String key)
    {
        return (Short)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getInt(String key)
    {
        return (Integer)itsPayload.get( key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getLong(String key)
    {
        return (Long)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public float 
    getFloat(String key)
    {
        return (Float)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public double 
    getDouble(String key)
    {
        return (Double)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getString(String key)
    {
        return (String)itsPayload.get(key);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    getObject(String key)
    {
        return itsPayload.get( key );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Collection<String> 
    getItemKeys()
    {
        return itsPayload.keySet();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasItem(String key)
    {
        return itsPayload.containsKey(key);
    }

}

// ##########################################################################
