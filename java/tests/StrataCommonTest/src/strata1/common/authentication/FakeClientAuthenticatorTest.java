// ##########################################################################
// # File Name:	FakeClientAuthenticatorTest.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.authentication;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class FakeClientAuthenticatorTest
{
    private IClientAuthenticator itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = 
            new FakeClientAuthenticator(
                new HashMap<String,String>()
                {
                    private static final long serialVersionUID = 1L;

                    {
                        put( "john","foobar");
                        put( "nay","FooBaz");
                    }
                });
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
    }

    /**
     * Test method for 
     * {@link FakeClientAuthenticator#authenticate(IClientCredential)}.
     */
    @Test
    public void 
    testAuthenticate()
        throws Exception
    {
        itsTarget.authenticate( 
            new UserNameAndPasswordCredential("john","foobar") );
        itsTarget.authenticate( 
            new UserNameAndPasswordCredential("nay","FooBaz") );
        itsTarget.authenticate( 
            new UserNameAndPasswordCredential("JOHN","foobar") );
        itsTarget.authenticate( 
            new UserNameAndPasswordCredential("NAY","FooBaz") );
    }

    /**
     * Test method for 
     * {@link FakeClientAuthenticator#authenticate(IClientCredential)}.
     */
    @Test
    public void 
    testAuthenticateException1()
        throws Exception
    {
        try
        {
            itsTarget.authenticate( 
                new UserNameAndPasswordCredential("jon","foobar") );
            fail( "Erroneously accepted unknown user name." );
        }
        catch (AuthenticationFailureException e)
        {
            assertTrue( e.hasInvalidField( "UserName" ) );
        }
    }

    /**
     * Test method for 
     * {@link FakeClientAuthenticator#authenticate(IClientCredential)}.
     */
    @Test
    public void 
    testAuthenticateException2()
        throws Exception
    {
        try
        {
            itsTarget.authenticate( 
                new UserNameAndPasswordCredential("john","FOOBAR") );
            fail( "Erroneously accepted incorrect password." );
        }
        catch (AuthenticationFailureException e)
        {
            assertTrue( e.hasInvalidField( "Password" ) );
        }
    }

}

// ##########################################################################
