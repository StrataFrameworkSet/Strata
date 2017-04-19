// ##########################################################################
// # File Name:	TaskDisruptorRouterTest.java
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

package strata.foundation.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.JavaLogEntryProcessor;
import strata.foundation.logger.Logger;
import strata.foundation.producerconsumer.DispatchKind;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TaskBlockingQueueRouterTest
{
    private ITaskPriorityDispatcher  itsTarget;
    private TestTaskPriorityProducer itsProducer1;
    private TestTaskPriorityProducer itsProducer2;
    private ILogger                  itsLogger;
    private ITaskConsumer            itsConsumer1;
    private ITaskConsumer            itsConsumer2;
    private ITaskConsumer            itsConsumer3;
    private StringWriter             itsWriter;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new TaskBlockingQueueDispatcher();
        
        itsWriter = new StringWriter();       
        itsLogger = createLogger();
        
        itsProducer1 = new TestTaskPriorityProducer(DispatchKind.ROUTE);
        itsProducer2 = new TestTaskPriorityProducer(DispatchKind.ROUTE);
        
        itsProducer1
            .insertTask( new TestTask("TestA",1,itsLogger) )
            .insertTask( new TestTask("TestB",2,itsLogger) )
            .insertTask( new TestTask("TestC",3,itsLogger) );
        
        itsProducer2
            .insertTask( new TestTask("TestD",1,itsLogger) )
            .insertTask( new TestTask("TestE",2,itsLogger) )
            .insertTask( new TestTask("TestF",3,itsLogger) );
       
        itsProducer1.setPriorityDispatcher( itsTarget );
        itsProducer2.setPriorityDispatcher( itsTarget );
        
        itsConsumer1 = new TaskConsumer(new TestTaskSelector(1),itsLogger);
        itsConsumer2 = new TaskConsumer(new TestTaskSelector(2),itsLogger);
        itsConsumer3 = new TaskConsumer(new TestTaskSelector(3),itsLogger);
        
        itsTarget
            .attachConsumer( itsConsumer1 )
            .attachConsumer( itsConsumer2 )
            .attachConsumer( itsConsumer3 );
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
        itsTarget.stopDispatching();
    }

    @Test
    public void
    testDispatch1()
    {
        itsTarget.startDispatching();
        
        itsProducer1.startProducing();
        itsProducer2.startProducing();

        try
        {
            Thread.sleep( 1000L );
        }
        catch(InterruptedException e) {}

        System
            .out
            .println(itsWriter.toString());
    }
    
    /**
     * Test method for {@link strata1.common.producerconsumer.ProducerConsumerManager#attachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testAttachConsumer()
    {
        ITaskConsumer consumer = new TaskConsumer(new TestTaskSelector(100),null);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.ProducerConsumerManager#detachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testDetachConsumer()
    {
        ITaskConsumer consumer = new TaskConsumer(new TestTaskSelector(100),null);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));
        itsTarget.detachConsumer( consumer );
        assertFalse( itsTarget.hasConsumer( consumer ));
    }


    protected ILogger
    createLogger()
    {
        ILogger logger = new Logger();
        
        logger.attachProcessor( new JavaLogEntryProcessor() );
        return logger;
    }
}

// ##########################################################################
