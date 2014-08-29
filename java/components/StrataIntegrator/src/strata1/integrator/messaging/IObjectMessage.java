// ##########################################################################
// # File Name:	IObjectMessage.java
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
interface IObjectMessage
    extends IMessage
{
    public IObjectMessage 
    setMessageId(String messageId);
    
    public IObjectMessage
    setCorrelationId(String correlationId);
    
    public IObjectMessage
    setReturnAddress(String returnAddress);
    
    public IObjectMessage
    setDeliveryMode(DeliveryMode mode);

    public IObjectMessage
    setByteProperty(String name,byte value);
    
    public IObjectMessage
    setBooleanProperty(String name,boolean value);
    
    public IObjectMessage
    setShortProperty(String name,short value);
    
    public IObjectMessage
    setIntProperty(String name,int value);
    
    public IObjectMessage
    setLongProperty(String name,long value);
    
    public IObjectMessage
    setFloatProperty(String name,float value);
    
    public IObjectMessage
    setDoubleProperty(String name,double value);
    
    public IObjectMessage
    setStringProperty(String name,String value);

    public IObjectMessage
    setPayload(Serializable payload);
    
    public Object
    getPayload();
}

// ##########################################################################
