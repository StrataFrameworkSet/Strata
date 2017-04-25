// ##########################################################################
// # File Name:	InMemoryMessageReceiverTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.inmemorymessaging;

import java.util.HashMap;
import java.util.Map;
import strata.integration.messaging.IMessage;
import strata.integration.messaging.IMessagingSession;
import strata.integration.messaging.ISelector;
import strata.integration.messaging.MessageReceiverTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryMessageReceiverTest
    extends MessageReceiverTest
{
    private final Map<String,ISelector> itsSelectors;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageReceiverTest}. 
     *
     */
    public 
    InMemoryMessageReceiverTest() 
    {
        Map<String,String> properties = new HashMap<String,String>();
       
        itsSelectors = new HashMap<String,ISelector>();
        
        properties.put( "ReturnAddress","foo" );
        
        itsSelectors.put( 
            "ReturnAddress='foo'",
            new SimpleSelector( properties ) );
        itsSelectors.put( 
            "FooProperty >= 5",
            new ISelector()
            {
                @Override
                public boolean 
                evaluate(IMessage message)
                {
                    if ( !message.hasProperty( "FooProperty" ) )
                        return false;
                    
                    return
                        message.getIntProperty( "FooProperty" ) >= 5;
                }
                
            });
        itsSelectors.put( 
            "FooProperty < 5",
            new ISelector()
            {
                @Override
                public boolean 
                evaluate(IMessage message)
                {
                    if ( !message.hasProperty( "FooProperty" ) )
                        return false;
                    
                    return
                        message.getIntProperty( "FooProperty" ) < 5;
                }
                
            });
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSession()
    {
        return new InMemoryMessagingSession(itsSelectors);
    }

}

// ##########################################################################
