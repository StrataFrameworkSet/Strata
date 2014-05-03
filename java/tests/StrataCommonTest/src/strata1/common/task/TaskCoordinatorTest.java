// ##########################################################################
// # File Name:	TaskCoordinatorTest.java
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

package strata1.common.task;

import static org.junit.Assert.*;
import strata1.common.logger.ILogger;
import strata1.common.logger.Logger;
import strata1.common.logger.PrintWriterLogEntryProcessor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.PrintWriter;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TaskCoordinatorTest
{
    private ITaskCoordinator itsTarget;
    private TestTaskProducer itsProducer1;
    private TestTaskProducer itsProducer2;
    private ILogger          itsLogger;
    private ITaskConsumer    itsConsumer1;
    private ITaskConsumer    itsConsumer2;
    private ITaskConsumer    itsConsumer3;

    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new TaskCoordinator();
        itsProducer1 = new TestTaskProducer();
        itsProducer2 = new TestTaskProducer();
        
        itsLogger    = new Logger();
        itsLogger.attachProcessor( 
            new PrintWriterLogEntryProcessor(new PrintWriter(System.out)));
        
        itsConsumer1 = 
            new TaskConsumer(
                new ITaskSelector()
                {
                    @Override
                    public boolean 
                    match(ITask task)
                    {
                        if ( !task.hasProperty( Integer.class,"taskId" ) )
                            return false;
                        
                        return 
                            task.getProperty(Integer.class,"taskId")==1; 
                    }
                    
                },
                itsLogger);
        itsConsumer2 = 
            new TaskConsumer(
                new ITaskSelector()
                {
                    @Override
                    public boolean 
                    match(ITask task)
                    {
                        if ( !task.hasProperty( Integer.class,"taskId" ) )
                            return false;
                        
                        return 
                            task.getProperty(Integer.class,"taskId")==2; 
                    }
                    
                },
                itsLogger);
        itsConsumer3 = 
            new TaskConsumer(
                new ITaskSelector()
                {
                    @Override
                    public boolean 
                    match(ITask task)
                    {
                        if ( !task.hasProperty( Integer.class,"taskId" ) )
                            return false;
                        
                        return 
                            task.getProperty(Integer.class,"taskId")==3; 
                    }
                    
                },
                itsLogger);
        
        itsTarget
            .attachProducer( itsProducer1 );
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
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#attachProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testAttachProducer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#attachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testAttachConsumer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#detachProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testDetachProducer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#detachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testDetachConsumer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#getProducers()}.
     */
    @Test
    public void 
    testGetProducers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#getConsumers()}.
     */
    @Test
    public void 
    testGetConsumers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#hasProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testHasProducer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#hasConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testHasConsumer()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startProducers()}.
     */
    @Test
    public void 
    testStartProducers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startConsumers()}.
     */
    @Test
    public void 
    testStartConsumers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#stopProducers()}.
     */
    @Test
    public void 
    testStopProducers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#stopConsumers()}.
     */
    @Test
    public void 
    testStopConsumers()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startUp()}.
     */
    @Test
    public void 
    testStartUp()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#shutDown()}.
     */
    @Test
    public void 
    testShutDown()
    {
        fail( "Not yet implemented" );
    }

}

// ##########################################################################
