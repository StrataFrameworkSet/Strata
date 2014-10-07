// ##########################################################################
// # File Name:	SqsMessagingSessionTest.java
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

import strata1.integrator.messaging.IMessagingSession;
import strata1.integrator.messaging.MessagingSessionTest;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQSClient;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMessagingSessionTest
    extends MessagingSessionTest
{

    /************************************************************************
     * Creates a new {@code SqsMessagingSessionTest}. 
     *
     */
    public 
    SqsMessagingSessionTest() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMessagingSession 
    createMessagingSesssion()
    {
        // AccessKeyId = AKIAIUYU4ICABB3XGYMA
        // SecretAccessKey = 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
                
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
                    new DefaultSelector() );
    }

}

// ##########################################################################
