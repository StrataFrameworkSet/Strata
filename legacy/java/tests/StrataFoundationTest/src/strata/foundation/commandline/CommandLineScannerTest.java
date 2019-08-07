// ##########################################################################
// # File Name:	CommandLineScannerTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommonTest Framework.
// #
// #   			The StrataCommonTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommonTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommonTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.commandline;

import static org.junit.Assert.assertEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandLineScannerTest
{
    private ICommandLineScanner itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new CommandLineScanner();
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
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext1()
    {
        String[] arguments = {};
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.DONE,actual.getKind() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext2()
    {
        String[] arguments = { "-a" };
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual.getKind() );
        assertEquals( "-a",actual.getInput() );
        assertEquals( "a",actual.getBuffer() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext3()
    {
        String[] arguments = { "-foo" };
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual.getKind() );
        assertEquals( "-foo",actual.getInput() );
        assertEquals( "foo",actual.getBuffer() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext4()
    {
        String[] arguments = { "--foo" };
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual.getKind() );
        assertEquals( "--foo",actual.getInput() );
        assertEquals( "foo",actual.getBuffer() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext5()
    {
        String[] arguments = { "-?" };
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual.getKind() );
        assertEquals( "-?",actual.getInput() );
        assertEquals( "?",actual.getBuffer() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext6()
    {
        String[] arguments = { "--foo-bar" };
        IToken   actual = null;
        
        actual = 
            itsTarget
                .setInput( arguments )
                .getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual.getKind() );
        assertEquals( "--foo-bar",actual.getInput() );
        assertEquals( "foo-bar",actual.getBuffer() );
    }


    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext7()
    {
        String[] arguments = { "-a","-foo","--foo","--!@#$%^&*()" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        IToken   actual4 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        actual4 = itsTarget.getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual1.getKind() );
        assertEquals( "-a",actual1.getInput() );
        assertEquals( "a",actual1.getBuffer() );
        
        assertEquals( TokenKind.OPTION_ID,actual2.getKind() );
        assertEquals( "-foo",actual2.getInput() );
        assertEquals( "foo",actual2.getBuffer() );
        
        assertEquals( TokenKind.OPTION_ID,actual3.getKind() );
        assertEquals( "--foo",actual3.getInput() );
        assertEquals( "foo",actual3.getBuffer() );
        
        assertEquals( TokenKind.OPTION_ID,actual4.getKind() );
        assertEquals( "--!@#$%^&*()",actual4.getInput() );
        assertEquals( "!@#$%^&*()",actual4.getBuffer() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext8()
    {
        String[] arguments = { "-a=x" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual1.getKind() );
        assertEquals( "-a",actual1.getInput() );
        assertEquals( "a",actual1.getBuffer() );
        
        assertEquals( TokenKind.OPTION_VALUE,actual2.getKind() );
        assertEquals( "=x",actual2.getInput() );
        assertEquals( "x",actual2.getBuffer() );
        
        assertEquals( TokenKind.DONE,actual3.getKind() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext9()
    {
        String[] arguments = { "--foo=\"x + y\"" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual1.getKind() );
        assertEquals( "--foo",actual1.getInput() );
        assertEquals( "foo",actual1.getBuffer() );
        
        assertEquals( TokenKind.OPTION_VALUE,actual2.getKind() );
        assertEquals( "=\"x + y\"",actual2.getInput() );
        assertEquals( "\"x + y\"",actual2.getBuffer() );
        
        assertEquals( TokenKind.DONE,actual3.getKind() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext10()
    {
        String[] arguments = { "--foo=\"x + y = - _\"" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        
        assertEquals( TokenKind.OPTION_ID,actual1.getKind() );
        assertEquals( "--foo",actual1.getInput() );
        assertEquals( "foo",actual1.getBuffer() );
        
        assertEquals( TokenKind.OPTION_VALUE,actual2.getKind() );
        assertEquals( "=\"x + y = - _\"",actual2.getInput() );
        assertEquals( "\"x + y = - _\"",actual2.getBuffer() );
        
        assertEquals( TokenKind.DONE,actual3.getKind() );
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext11()
    {
        String[] arguments = { "a","foo","/!@#$/%^&*()" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        IToken   actual4 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        actual4 = itsTarget.getNext();
        
        assertEquals( TokenKind.PARAMETER_VALUE,actual1.getKind() );
        assertEquals( "a",actual1.getInput() );
        assertEquals( "a",actual1.getBuffer() );
        
        assertEquals( TokenKind.PARAMETER_VALUE,actual2.getKind() );
        assertEquals( "foo",actual2.getInput() );
        assertEquals( "foo",actual2.getBuffer() );
        
        assertEquals( TokenKind.PARAMETER_VALUE,actual3.getKind() );
        assertEquals( "/!@#$/%^&*()",actual3.getInput() );
        assertEquals( "/!@#$/%^&*()",actual3.getBuffer() );
        
        assertEquals( TokenKind.DONE,actual4.getKind() );

    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     */
    @Test
    public void 
    testGetNext12()
    {
        String[] arguments = { "\"-a foo\"" };
        IToken   actual1 = null;
        IToken   actual2 = null;
        IToken   actual3 = null;
        
        itsTarget.setInput( arguments );
        
        actual1 = itsTarget.getNext();
        actual2 = itsTarget.getNext();
        actual3 = itsTarget.getNext();
        
        assertEquals( TokenKind.PARAMETER_VALUE,actual1.getKind() );
        assertEquals( "\"-a foo\"",actual1.getInput() );
        assertEquals( "\"-a foo\"",actual1.getBuffer() );

        assertEquals( TokenKind.DONE,actual2.getKind() );
        assertEquals( TokenKind.DONE,actual3.getKind() );

    }

}

// ##########################################################################
