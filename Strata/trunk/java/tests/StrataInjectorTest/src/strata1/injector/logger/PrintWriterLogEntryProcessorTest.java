// ##########################################################################
// # File Name:	PrintWriterLogEntryProcessorTest.java
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

package strata1.injector.logger;

import static org.junit.Assert.*;
import strata1.common.logger.ILogEntry;
import strata1.common.logger.LogEntry;
import strata1.common.logger.LoggingLevel;
import strata1.common.logger.PrintWriterLogEntryProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintWriter;
import java.io.StringWriter;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PrintWriterLogEntryProcessorTest
{
    private StringWriter                 itsOutput;
    private PrintWriterLogEntryProcessor itsTarget;
    
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
        itsOutput = new StringWriter();
        itsTarget = new PrintWriterLogEntryProcessor(
            new PrintWriter(itsOutput) );
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
        itsOutput = null;
    }

    /**
     * Test method for {@link PrintWriterLogEntryProcessor#process(ILogEntry)}.
     */
    @Test
    public void 
    testProcess()
    {
        ILogEntry entry = new LogEntry( LoggingLevel.INFO,"This is a test." );
        String    expected = entry.getTimestamp().toString() + "  INFO       This is a test.";
        
        itsTarget.process( entry );
        
        assertEquals(
            expected,
            itsOutput.toString() );
    }

    /**
     * Test method for {@link PrintWriterLogEntryProcessor#setFormat(jString)}.
     */
    @Test
    public void 
    testSetGetFormat()
    {
        String expected = "%1$s %2$s %3$s";
        
        itsTarget.setFormat( expected );
        assertEquals( expected,itsTarget.getFormat() );
    }

}

// ##########################################################################
