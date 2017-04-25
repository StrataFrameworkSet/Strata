// ##########################################################################
// # File Name:	SqsObjectMessageTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegratorTest Framework.
// #
// #   			The StrataSqsIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.sqsmessaging;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import strata.integration.messaging.IObjectMessage;
import strata.integration.messaging.ObjectMessageTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsObjectMessageTest
    extends ObjectMessageTest
{
    private final ISqsMessagingSession itsSession;

    /************************************************************************
     * Creates a new SqsObjectMessageTest. 
     *
     */
    public 
    SqsObjectMessageTest() 
    {
        itsSession = new SqsQueueMessagingSession(
            new DefaultAWSCredentialsProviderChain().getCredentials());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IObjectMessage 
    getTarget()
    {
        return new SqsObjectMessage(itsSession);
    }

}

// ##########################################################################
