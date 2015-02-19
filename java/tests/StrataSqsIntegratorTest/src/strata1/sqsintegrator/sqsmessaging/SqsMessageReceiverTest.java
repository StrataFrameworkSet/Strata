// ##########################################################################
// # File Name:	SqsMessageReceiverTest.java
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

package strata1.sqsintegrator.sqsmessaging;

import strata1.integrator.inmemorymessaging.SimpleSelector;
import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.MessageReceiverTest;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClient;
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMessageReceiverTest
    extends MessageReceiverTest
{
    
    /************************************************************************
     * Creates a new {@code SqsMessageReceiverTest}. 
     *
     */
    public 
    SqsMessageReceiverTest() 
    {
     }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSession()
    {
        // AccessKeyId = AKIAIUYU4ICABB3XGYMA
        // SecretAccessKey = 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
        
        Map<String,String> properties = new HashMap<String,String>();
           
        properties.put( "ReturnAddress","foo" );
        
        return 
            new SqsQueueMessagingSession(
                new BasicAWSCredentials(
                    "AKIAIUYU4ICABB3XGYMA",
                    "GOes989CWbXzYX8xeQaziMiha0CFfVU49ZT8Q/cr"))
                .insertQueue( 
                    "foo.test",
                    "https://sqs.us-west-2.amazonaws.com/450471544890/foo-test" )
                .insertSelector( 
                    "ReturnAddress='foo'",
                    new SimpleSelector(properties) )
                .insertSelector( 
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
                        
                    })
                .insertSelector( 
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
    protected long 
    getCleanupTimeout()
    {
        return 5000;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    sleepIfNeeded(long millis)
    {
        try
        {
            Thread.sleep( millis );
        }
        catch(InterruptedException e) {}
    }

}

// ##########################################################################