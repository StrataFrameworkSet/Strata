// ##########################################################################
// # File Name:	SqsMapMessage.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.sqsmessaging;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.util.Base64;
import strata.integration.messaging.DeliveryMode;
import strata.integration.messaging.IMapMessage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMapMessage
    extends    SqsMessage
    implements IMapMessage
{
    private final Map<String,String> itsPayload;
    
    /************************************************************************
     * Creates a new SqsMapMessage. 
     *
     */
    public 
    SqsMapMessage(ISqsMessagingSession session)
    {
        super( session );
        setPayloadType( PayloadType.MAP );
        itsPayload = new HashMap<String,String>();
    }

    /************************************************************************
     * Creates a new SqsMapMessage. 
     *
     * @param imp
     */
    public 
    SqsMapMessage(ISqsMessagingSession session,Message imp)
    {
        super( session,imp );
        setPayloadType( PayloadType.MAP );
        itsPayload = fromString( imp.getBody() );
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
        itsPayload.put( key,new Byte(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setBytes(String key,byte[] item)
    {
        itsPayload.put( key,Base64.encodeAsString( item ) );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setBoolean(String key,boolean item)
    {
        itsPayload.put( key,new Boolean(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setChar(String key,char item)
    {
        itsPayload.put( key,new Character(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setShort(String key,short item)
    {
        itsPayload.put( key,new Short(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setInt(String key,int item)
    {
        itsPayload.put( key,new Integer(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setLong(String key,long item)
    {
        itsPayload.put( key,new Long(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setFloat(String key,float item)
    {
        itsPayload.put( key,new Float(item).toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMapMessage 
    setDouble(String key,double item)
    {
        itsPayload.put( key,new Double(item).toString() );
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
        itsPayload.put( key,item.toString() );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public byte 
    getByte(String key)
    {
        return new Byte(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public byte[] 
    getBytes(String key)
    {
        return Base64.decode( itsPayload.get(key) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    getBoolean(String key)
    {
        return new Boolean(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public char 
    getChar(String key)
    {
        return itsPayload.get(key).charAt( 0 );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public short 
    getShort(String key)
    {
        return new Short(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getInt(String key)
    {
        return new Integer(itsPayload.get( key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getLong(String key)
    {
        return new Long(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public float 
    getFloat(String key)
    {
        return new Float(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public double 
    getDouble(String key)
    {
        return new Double(itsPayload.get(key));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getString(String key)
    {
        return itsPayload.get(key);
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

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected Message 
    getMessageImp()
    {
        Message imp = super.getMessageImp();
        
        imp.setBody( toString( itsPayload ) );
        return imp;
    }

    /************************************************************************
     *  
     *
     * @param payload
     * @return
     */
    private Map<String,String> 
    fromString(String payload)
    {
        StringReader       reader = new StringReader(payload);
        MapEntryList       entries = null;
        Map<String,String> output = new HashMap<String,String>();;
        JAXBContext        context = null;
        Unmarshaller       unmarshaller = null;

        try
        {
            context = JAXBContext.newInstance(MapEntryList.class);
            unmarshaller = context.createUnmarshaller();
            entries = 
                (MapEntryList)unmarshaller.unmarshal( reader );
            /*
            entries = 
                ((JAXBElement<MapEntryList>)unmarshaller
                    .unmarshal(reader))
                    .getValue();
            */
            for (MapEntry entry:entries.getEntries())
                output.put( entry.key,entry.value );
            
            return output;
        }
        catch(JAXBException e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @param payload
     * @return
     */
    private String 
    toString(Map<String,String> payload)
    {
        MapEntryList entries    = new MapEntryList(payload);
        JAXBContext  context    = null;
        Marshaller   marshaller = null;
        StringWriter writer     = new StringWriter();
        
        try
        {
            context = JAXBContext.newInstance(MapEntryList.class);
            marshaller = context.createMarshaller();
            marshaller.marshal(entries,writer);
            return writer.toString();
        }
        catch (JAXBException e)
        {
            throw new IllegalStateException( e);
        }
    }

    
}

// ##########################################################################
