// ##########################################################################
// # File Name:	CommandLineProcessorTest.java
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

package strata1.common.commandline;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandLineProcessorTest
{
    private MockCommandLineProcessor itsTarget;
    private Set<String>              itsOptions;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() 
        throws Exception
    {
        itsTarget  = new MockCommandLineProcessor();
        itsOptions = new HashSet<String>(Arrays.asList( "-a","-b","-c" ));
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void 
    tearDown() 
        throws Exception
    {
        itsTarget = null;
    }

    /**
     * Test method for {@link strata1.common.commandline.ICommandLineProcessor#setOptions(java.util.Set)}.
     */
    @Test
    public void 
    testSetGetOptions()
    {
        itsTarget.setOptions( itsOptions );
        assertEquals( itsOptions,itsTarget.getOptions() );
    }

    /**
     * Test method for {@link strata1.common.commandline.ICommandLineProcessor#getArguments()}.
     */
    @Test
    public void 
    testGetArguments()
    {
        List<String> arguments = Arrays.asList( "-a","foo","-b","bar","-c","foobar" );
        
        itsTarget.process( arguments.toArray(new String[0]));
        assertEquals( arguments,itsTarget.getArguments() );
    }

    /**
     * Test method for {@link strata1.common.commandline.ICommandLineProcessor#process(java.lang.String[])}.
     */
    @Test
    public void 
    testProcess()
    {
        List<String> arguments = Arrays.asList( "-a","foo","-b","bar","-c","foobar","last" );
        
        itsTarget.setOptions( itsOptions );
        itsTarget.process( arguments.toArray(new String[0]));        
        itsTarget.assertNamedArgument( "-a","foo" );
        itsTarget.assertNamedArgument( "-b","bar" );
        itsTarget.assertNamedArgument( "-c","foobar" );
        itsTarget.assertPositionedArgument( 0,"last" );
    }

}

// ##########################################################################
