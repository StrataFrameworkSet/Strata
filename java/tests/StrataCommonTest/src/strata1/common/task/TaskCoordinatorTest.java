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
import java.util.Set;

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
        
        itsLogger = 
            new Logger()
                .attachProcessor( 
                    new PrintWriterLogEntryProcessor(new PrintWriter(System.out)));
        
        itsProducer1 = new TestTaskProducer();
        itsProducer2 = new TestTaskProducer();
        
        itsProducer1
            .insertTask( new TestTask("TestA",1,itsLogger) )
            .insertTask( new TestTask("TestB",2,itsLogger) )
            .insertTask( new TestTask("TestC",3,itsLogger) );
        
        itsProducer2
            .insertTask( new TestTask("TestD",1,itsLogger) )
            .insertTask( new TestTask("TestE",2,itsLogger) )
            .insertTask( new TestTask("TestF",3,itsLogger) );
       
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
            .attachProducer( itsProducer1 )
            .attachProducer( itsProducer2 )
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
        itsTarget.shutDown();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#attachProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testAttachProducer()
    {
        ITaskProducer producer = new TestTaskProducer();
        
        assertFalse( itsTarget.hasProducer( producer ));
        itsTarget.attachProducer( producer );
        assertTrue( itsTarget.hasProducer( producer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#attachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testAttachConsumer()
    {
        ITaskConsumer consumer = new TaskConsumer(null,null);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#detachProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testDetachProducer()
    {
        ITaskProducer producer = new TestTaskProducer();
        
        assertFalse( itsTarget.hasProducer( producer ));
        itsTarget.attachProducer( producer );
        assertTrue( itsTarget.hasProducer( producer ));
        itsTarget.detachProducer( producer );
        assertFalse( itsTarget.hasProducer( producer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#detachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testDetachConsumer()
    {
        ITaskConsumer consumer = new TaskConsumer(null,null);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));
        itsTarget.detachConsumer( consumer );
        assertFalse( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#getProducers()}.
     */
    @Test
    public void 
    testGetProducers()
    {
        Set<ITaskProducer> producers = itsTarget.getProducers();
        
        assertEquals( 2,producers.size() );
        assertTrue( producers.contains( itsProducer1 ));
        assertTrue( producers.contains( itsProducer2 ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#getConsumers()}.
     */
    @Test
    public void 
    testGetConsumers()
    {
        Set<ITaskConsumer> consumers = itsTarget.getConsumers();
        
        assertEquals( 3,consumers.size() );
        assertTrue( consumers.contains( itsConsumer1 ) );
        assertTrue( consumers.contains( itsConsumer2 ) );
        assertTrue( consumers.contains( itsConsumer3 ) );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#hasProducer(strata1.common.producerconsumer.IProducer)}.
     */
    @Test
    public void 
    testHasProducer()
    {
        ITaskProducer producer = new TestTaskProducer();
        
        assertFalse( itsTarget.hasProducer( producer ));
        itsTarget.attachProducer( producer );
        assertTrue( itsTarget.hasProducer( producer ));
        itsTarget.detachProducer( producer );
        assertFalse( itsTarget.hasProducer( producer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#hasConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testHasConsumer()
    {
        ITaskConsumer consumer = new TaskConsumer(null,null);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));
        itsTarget.detachConsumer( consumer );
        assertFalse( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startProducers()}.
     */
    @Test
    public void 
    testStartProducers()
    {
        itsTarget.startProducers();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startConsumers()}.
     */
    @Test
    public void 
    testStartConsumers()
    {
        itsTarget.startConsumers();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#stopProducers()}.
     */
    @Test
    public void 
    testStopProducers()
    {
        itsTarget.stopProducers();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#stopConsumers()}.
     */
    @Test
    public void 
    testStopConsumers()
    {
        itsTarget.stopConsumers();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#startUp()}.
     */
    @Test
    public void 
    testStartUp()
    {
        itsTarget.startUp();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.Coordinator#shutDown()}.
     */
    @Test
    public void 
    testShutDown()
    {
        itsTarget.shutDown();
    }

}

// ##########################################################################
