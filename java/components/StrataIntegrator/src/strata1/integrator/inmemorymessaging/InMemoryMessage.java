// ##########################################################################
// # File Name:	InMemoryMessage.java
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
import strata1.integrator.messaging.IMessage;
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class InMemoryMessage
    implements IMessage
{
    private String                   itsMessageId;
    private String                   itsCorrelationId;
    private String                   itsReturnAddress;
    private DeliveryMode             itsDeliveryMode;
    private long                     itsTimeToLive;
    private final Map<String,Object> itsProperties;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessage}. 
     *
     */
    protected 
    InMemoryMessage()
    {
        itsMessageId     = null;
        itsCorrelationId = null;
        itsReturnAddress = null;
        itsDeliveryMode  = DeliveryMode.NON_PERSISTENT;
        itsTimeToLive    = 10000;
        itsProperties    = new HashMap<String,Object>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setMessageId(String messageId)
    {
        itsMessageId = messageId;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setCorrelationId(String correlationId)
    {
        itsCorrelationId = correlationId;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setReturnAddress(String returnAddress)
    {
        itsReturnAddress = returnAddress;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDeliveryMode(DeliveryMode mode)
    {
        itsDeliveryMode = mode;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setByteProperty(String name,byte value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setBooleanProperty(String name,boolean value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setShortProperty(String name,short value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setIntProperty(String name,int value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setLongProperty(String name,long value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setFloatProperty(String name,float value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setDoubleProperty(String name,double value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessage 
    setStringProperty(String name,String value)
    {
        itsProperties.put( name,value );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getMessageId()
    {
        return itsMessageId;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getCorrelationId()
    {
        return itsCorrelationId;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getReturnAddress()
    {
        return itsReturnAddress;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public DeliveryMode 
    getDeliveryMode()
    {
        return itsDeliveryMode;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public byte 
    getByteProperty(String name)
    {
        return (byte)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    getBooleanProperty(String name)
    {
        return (boolean)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public short 
    getShortProperty(String name)
    {
        return (short)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getIntProperty(String name)
    {
        return (int)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getLongProperty(String name)
    {
        return (long)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public float 
    getFloatProperty(String name)
    {
        return (float)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public double 
    getDoubleProperty(String name)
    {
        return (double)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getStringProperty(String name)
    {
        return (String)itsProperties.get( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasProperty(String name)
    {
        return itsProperties.containsKey( name );
    }

    /************************************************************************
     *  
     *
     * @param timeToLive
     * @return
     */
    public IMessage 
    setTimeToLive(long timeToLive)
    {
        itsTimeToLive = timeToLive;
        return this;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public long 
    getTimeToLive()
    {
        return itsTimeToLive;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    acknowledge()
    {
    }
}

// ##########################################################################
