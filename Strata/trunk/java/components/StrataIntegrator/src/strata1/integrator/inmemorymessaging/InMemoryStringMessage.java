// ##########################################################################
// # File Name:	InMemoryStringMessage.java
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
import strata1.integrator.messaging.IStringMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryStringMessage
    extends    InMemoryMessage
    implements IStringMessage
{
    private String itsPayload;
    
    /************************************************************************
     * Creates a new {@code InMemoryStringMessage}. 
     *
     */
    public 
    InMemoryStringMessage()
    {
        itsPayload = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setMessageId(String messageId)
    {
        super.setMessageId( messageId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setCorrelationId(String correlationId)
    {
        super.setCorrelationId( correlationId );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setReturnAddress(String returnAddress)
    {
        super.setReturnAddress( returnAddress );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        super.setDeliveryMode( mode );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setTimeToLive(long timeToLive)
    {
        super.setTimeToLive( timeToLive );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setByteProperty(String name,byte value)
    {
        super.setByteProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setBooleanProperty(String name,boolean value)
    {
        super.setBooleanProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setShortProperty(String name,short value)
    {
        super.setShortProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setIntProperty(String name,int value)
    {
        super.setIntProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setLongProperty(String name,int value)
    {
        super.setLongProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setFloatProperty(String name,float value)
    {
        super.setFloatProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setDoubleProperty(String name,double value)
    {
        super.setDoubleProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setStringProperty(String name,String value)
    {
        super.setStringProperty( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStringMessage 
    setPayload(String payload)
    {
        itsPayload = payload;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPayload()
    {
        return itsPayload;
    }

}

// ##########################################################################
