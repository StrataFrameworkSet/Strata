// ##########################################################################
// # File Name:	IMapMessage.java
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

package strata1.integrator.messaging;

import java.util.Collection;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IMapMessage
    extends IMessage
{
    public IMapMessage 
    setMessageId(String messageId);
    
    public IMapMessage
    setCorrelationId(String correlationId);
    
    public IMapMessage
    setReturnAddress(String returnAddress);
    
    public IMapMessage
    setDeliveryMode(DeliveryMode mode);
    
    public IMapMessage 
    setTimeToLive(long timeToLive);

    public IMapMessage
    setByteProperty(String name,byte value);
    
    public IMapMessage
    setBooleanProperty(String name,boolean value);
    
    public IMapMessage
    setShortProperty(String name,short value);
    
    public IMapMessage
    setIntProperty(String name,int value);
    
    public IMapMessage
    setLongProperty(String name,int value);
    
    public IMapMessage
    setFloatProperty(String name,float value);
    
    public IMapMessage
    setDoubleProperty(String name,double value);
    
    public IMapMessage
    setStringProperty(String name,String value);

    public IMapMessage
    setByte(String key,byte item);
    
    public IMapMessage
    setBytes(String key,byte[] item);
    
    public IMapMessage
    setBoolean(String key,boolean item);
    
    public IMapMessage
    setChar(String key,char item);
    
    public IMapMessage
    setShort(String key,short item);
    
    public IMapMessage
    setInt(String key,int item);
    
    public IMapMessage
    setLong(String key,long item);
    
    public IMapMessage
    setFloat(String key,float item);
    
    public IMapMessage
    setDouble(String key,double item);
    
    public IMapMessage
    setString(String key,String item);
    
    public IMapMessage
    setObject(String key,Object item);
    
    public byte
    getByte(String key);
    
    public byte[]
    getBytes(String key);
    
    public boolean
    getBoolean(String key);
    
    public char
    getChar(String key);
    
    public short
    getShort(String key);
    
    public int
    getInt(String key);
        
    public long
    getLong(String key);
    
    public float
    getFloat(String key);
    
    public double
    getDouble(String key);
    
    public String
    getString(String key);
    
    public Object
    getObject(String key);
    
    public Collection<String>
    getItemKeys();
    
    public boolean
    hasItem(String key);

}

// ##########################################################################
