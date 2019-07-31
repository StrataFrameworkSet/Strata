// ##########################################################################
// # File Name:	SimpleSelector.java
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

package strata.integration.inmemorymessaging;

import java.util.HashMap;
import java.util.Map;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.ISelector;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SimpleSelector
    implements ISelector
{
    private final Map<String,String> itsProperties;
    
    private static final int MESSAGE_ID = 0;
    private static final int CORRELATION_ID = 1;
    private static final int RETURN_ADDRESS = 2;
    private static final int USER_PROPERTY = 3;
    
    
    /************************************************************************
     * Creates a new SimpleSelector. 
     *
     */
    public 
    SimpleSelector(final Map<String,String> properties)
    {
        //itsProperties = new HashMap<String,String>( properties );
        itsProperties = new HashMap<String,String>();
        
        for (String key:properties.keySet())
            itsProperties.put( key,properties.get( key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    evaluate(IMessage message)
    {
        String value = null;
        
        for (String key:itsProperties.keySet())
        { 
            switch ( getPropertyType( key ) )
            {
            case MESSAGE_ID:               
                value = message.getMessageId();
                break;
                
            case CORRELATION_ID:
                value = message.getCorrelationId();
                break;
                
            case RETURN_ADDRESS:
                value = message.getReturnAddress();
                break;
                
            default:
                value = message.getStringProperty( key );
                break;
            }
            
            if ( value == null )
                return false;
            
            if ( !value.equals( itsProperties.get( key ) ) )
                return false;
        }
        
        return true;
    }

    /************************************************************************
     *  
     *
     * @param key
     * @return
     */
    private int 
    getPropertyType(String key)
    {
        if ( key.equalsIgnoreCase( "MessageId" ) )
            return MESSAGE_ID;
        
        if ( key.equalsIgnoreCase( "CorrelationId" ) )
            return CORRELATION_ID;
        
        if ( key.equalsIgnoreCase( "ReturnAddress" ) )
            return RETURN_ADDRESS;
        
        return USER_PROPERTY;
    }

}

// ##########################################################################
