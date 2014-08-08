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

package strata1.common.commandline;

import static org.junit.Assert.*;
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
class CommandLineParserTest
{
    private ICommandLineParser itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new CommandLineParser();
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
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext1() 
        throws Throwable
    {
        String[] arguments = {};
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("") )
            .parse( arguments );        
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext2() throws Throwable
    {
        String[] arguments = { "-a" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("-a") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext3() throws Throwable
    {
        String[] arguments = { "-foo" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("-foo") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext4() throws Throwable
    {
        String[] arguments = { "--foo" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("--foo") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext5() throws Throwable
    {
        String[] arguments = { "-?" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("-?") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext6() throws Throwable
    {
        String[] arguments = { "--foo-bar" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("--foo-bar") )
            .parse( arguments );            
    }


    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext7() throws Throwable
    {
        String[] arguments = { "-a","-foo","--foo","--!@#$%^&*()" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("-a -foo --foo --!@#$%^&*()") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext8() throws Throwable
    {
        String[] arguments = { "-a=x" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("-a=x") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext9() throws Throwable
    {
        String[] arguments = { "--foo=\"x + y\"" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("--foo=\"x + y\"") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext10() throws Throwable
    {
        String[] arguments = { "--foo=\"x + y = - _\"" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("--foo=\"x + y = - _\"") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext11() throws Throwable
    {
        String[] arguments = { "a","foo","/!@#$/%^&*()" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("a foo /!@#$/%^&*()") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext12() throws Throwable
    {
        String[] arguments = { "\"-a foo\"" };
        
        itsTarget
            .setProcessor( new MockCommandLineProcessor("\"-a foo\"") )
            .parse( arguments );            
    }

    /**
     * Test method for {@link ICommandLineScanner#getNext()}.
     * @throws Throwable 
     */
    @Test
    public void 
    testGetNext13() throws Throwable
    {
        String[] arguments = 
            { 
                "true",
                "7",
                "999999999999",
                "3.5", 
                "999999999999.99999999999", 
                "3.57", 
                "C:/temp/foo/bar", 
                "357.11", 
                "04/25/1967",
                "--boolean=true",
                "--integer=7",
                "--long=999999999999",
                "--float=3.5", 
                "--double=999999999999.99999999999", 
                "--decimal=3.57", 
                "--path=C:/temp/foo/bar", 
                "--money=357.11", 
                "--datetime=04/25/1967" };
        
        itsTarget
            .setProcessor( new ExampleCommandLineProcessor() )
            .parse( arguments );            
    }

}

// ##########################################################################
