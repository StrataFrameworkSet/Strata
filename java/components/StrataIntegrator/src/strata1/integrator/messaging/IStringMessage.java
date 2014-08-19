// ##########################################################################
// # File Name: IStringMessage.java
// #
// # Copyright: 2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataIntegrator Framework.
// #
// #            The StrataIntegrator Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataIntegrator Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataIntegrator
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.messaging;

public 
interface IStringMessage
    extends IMessage
{
    public IStringMessage 
    setMessageId(String messageId);
    
    public IStringMessage
    setCorrelationId(String correlationId);
    
    public IStringMessage
    setReturnAddress(String returnAddress);
    
    public IStringMessage
    setDeliveryMode(DeliveryMode mode);
    
    public IStringMessage 
    setTimeToLive(long timeToLive);

    public IStringMessage
    setByteProperty(String name,byte value);
    
    public IStringMessage
    setBooleanProperty(String name,boolean value);
    
    public IStringMessage
    setShortProperty(String name,short value);
    
    public IStringMessage
    setIntegerProperty(String name,int value);
    
    public IStringMessage
    setLongProperty(String name,int value);
    
    public IStringMessage
    setFloatProperty(String name,float value);
    
    public IStringMessage
    setDoubleProperty(String name,double value);
    
    public IStringMessage
    setStringProperty(String name,String value);

    public IStringMessage
    setPayload(String payload);
    
    public String
    getPayload();
}

// ##########################################################################
