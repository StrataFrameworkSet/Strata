// ##########################################################################
// # File Name:	IBytesMessage.java
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

package strata1.integrator.messaging;

import java.io.Serializable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IBytesMessage
    extends IMessage
{
    public IBytesMessage 
    setMessageId(String messageId);
    
    public IBytesMessage
    setCorrelationId(String correlationId);
    
    public IBytesMessage
    setReturnAddress(String returnAddress);
    
    public IBytesMessage
    setDeliveryMode(DeliveryMode mode);

    public IBytesMessage
    setByteProperty(String name,byte value);
    
    public IBytesMessage
    setBooleanProperty(String name,boolean value);
    
    public IBytesMessage
    setShortProperty(String name,short value);
    
    public IBytesMessage
    setIntProperty(String name,int value);
    
    public IBytesMessage
    setLongProperty(String name,long value);
    
    public IBytesMessage
    setFloatProperty(String name,float value);
    
    public IBytesMessage
    setDoubleProperty(String name,double value);
    
    public IBytesMessage
    setStringProperty(String name,String value);

    public IBytesMessage
    setPayload(byte[] payload);
    
    public byte[]
    getPayload();
}

// ##########################################################################
