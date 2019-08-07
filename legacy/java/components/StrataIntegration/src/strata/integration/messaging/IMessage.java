// ##########################################################################
// # File Name:	IMessage.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.integration.messaging;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IMessage
{
    public IMessage 
    setMessageId(String messageId);
    
    public IMessage
    setCorrelationId(String correlationId);
    
    public IMessage 
    setSequenceNum(long sequenceNum);
    
    public IMessage
    setReturnAddress(String returnAddress);
    
    public IMessage
    setDeliveryMode(DeliveryMode mode);

    public IMessage
    setByteProperty(String name,byte value);
    
    public IMessage
    setBooleanProperty(String name,boolean value);
    
    public IMessage
    setShortProperty(String name,short value);
    
    public IMessage
    setIntProperty(String name,int value);
    
    public IMessage
    setLongProperty(String name,long value);
    
    public IMessage
    setFloatProperty(String name,float value);
    
    public IMessage
    setDoubleProperty(String name,double value);
    
    public IMessage
    setStringProperty(String name,String value);
    
    public String
    getMessageId();
    
    public String 
    getCorrelationId();
    
    public long 
    getSequenceNum();
    
    public String 
    getReturnAddress();
    
    public DeliveryMode
    getDeliveryMode();
    
    public byte
    getByteProperty(String name);
    
    public boolean
    getBooleanProperty(String name);
    
    public short
    getShortProperty(String name);
    
    public int
    getIntProperty(String name);
    
    public long
    getLongProperty(String name);
    
    public float
    getFloatProperty(String name);
    
    public double
    getDoubleProperty(String name);
    
    public String
    getStringProperty(String name);
    
    public boolean
    hasProperty(String name);
    
    public void
    acknowledge();
}

// ##########################################################################
