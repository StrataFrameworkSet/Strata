// ##########################################################################
// # File Name:	SqsMapMessageTest.java
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

package strata1.sqsintegrator.sqsmessaging;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import strata1.integrator.messaging.IMapMessage;
import strata1.integrator.messaging.MapMessageTest;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SqsMapMessageTest
    extends MapMessageTest
{
    private final ISqsMessagingSession itsSession;
    
    /************************************************************************
     * Creates a new SqsMapMessageTest. 
     *
     */
    public 
    SqsMapMessageTest() 
    {
        itsSession = new SqsQueueMessagingSession(
            new DefaultAWSCredentialsProviderChain().getCredentials());
    }

    @Test
    public void
    testConstructor()
    {
        SqsMapMessage expected = new SqsMapMessage(itsSession);
        SqsMapMessage actual = null;
        
        expected
            .setBoolean( "Foo",true )
            .setInt( "Bar",5 )
            .setString( "FooBar","xxxxxxx" );
        
        actual = new SqsMapMessage( itsSession,expected.getMessageImp() ); 
        assertEquals(expected.getBoolean("Foo"),actual.getBoolean("Foo"));
        assertEquals(expected.getInt("Bar"),actual.getInt("Bar"));
        assertEquals(expected.getString("FooBar"),actual.getString("FooBar"));  
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IMapMessage 
    getTarget()
    {
        return new SqsMapMessage(itsSession);
    }

}

// ##########################################################################
