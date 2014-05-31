// ##########################################################################
// # File Name:	DisruptorLogEntryProcessorTest.java
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

package strata1.common.logger;

import static org.junit.Assert.*;
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
class DisruptorLogEntryProcessorTest
{
    private ILogEntryDispatcher        itsDispatcher;
    private StringWriter               itsOutput;
    private ILogEntryConsumer          itsConsumer;
    private DisruptorLogEntryProcessor itsTarget;
    
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
        itsDispatcher = new LogEntryDisruptorBroadcaster(1024);
        itsOutput = new StringWriter();
        itsConsumer = 
            new PrintWriterLogEntryConsumer(
                new PrintWriter(itsOutput) );
        itsDispatcher.attachConsumer( itsConsumer );
        itsTarget = new DisruptorLogEntryProcessor(itsDispatcher);
        itsDispatcher.startDispatching();
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
        itsDispatcher.stopDispatching();
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
        String    expected = entry.getTimestamp().toString() + "  INFO       This is a test.\n\n";
        
        itsTarget.process( entry );
        
        try
        {
            Thread.sleep( 100L );
        }
        catch(InterruptedException e) {}
        
        assertEquals(
            expected,
            itsOutput.toString() );
    }


}

// ##########################################################################
