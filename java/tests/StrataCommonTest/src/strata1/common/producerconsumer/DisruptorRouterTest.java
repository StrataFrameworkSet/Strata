// ##########################################################################
// # File Name:	DisruptorRouterTest.java
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

package strata1.common.producerconsumer;

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
class DisruptorRouterTest
{
    private static final int        MAX = 10000000;
    
    private ICountRequestDispatcher itsTarget;
    private ICountRequestProducer   itsProducer1;    
    private ICountRequestProducer   itsProducer2;
    private ICountRequestProducer   itsProducer3;
    private ICountRequestConsumer   itsConsumer1;
    private ICountRequestConsumer   itsConsumer2;
    private ICountRequestConsumer   itsConsumer3;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new CountRequestDisruptorRouter(16384);
        itsProducer1 = new CountRequestProducer(1,MAX);
        itsProducer2 = new CountRequestProducer(2,MAX);
        itsProducer3 = new CountRequestProducer(3,MAX);
        itsConsumer1 = new CountRequestConsumer(1);
        itsConsumer2 = new CountRequestConsumer(2);
        itsConsumer3 = new CountRequestConsumer(3);
        
        itsProducer1.setDispatcher( itsTarget );
        itsProducer2.setDispatcher( itsTarget );
        itsProducer3.setDispatcher( itsTarget );
        
        itsTarget.attachConsumer( itsConsumer1 );
        itsTarget.attachConsumer( itsConsumer2 );
        itsTarget.attachConsumer( itsConsumer3 );
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
        itsProducer1.stopProducing();
        itsProducer2.stopProducing();
        itsProducer3.stopProducing();
      
        itsTarget.stopDispatching();
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#attachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testAttachConsumer()
    {
        ICountRequestConsumer  consumer = new CountRequestConsumer(100);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer ));       
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#detachConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testDetachConsumer()
    {
        ICountRequestConsumer  consumer = new CountRequestConsumer(100);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer )); 
        itsTarget.detachConsumer( consumer );
        assertFalse( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#dispatch(java.lang.Object)}.
     * @throws InterruptedException 
     */
    @Test
    public void 
    testDispatch1() throws InterruptedException
    {
        
        assertEquals( 0,itsProducer1.getCount() );
        assertEquals( 0,itsProducer2.getCount() );
        assertEquals( 0,itsProducer3.getCount() );
        
        assertEquals( 0,itsConsumer1.getCount() );
        assertEquals( 0,itsConsumer2.getCount() );
        assertEquals( 0,itsConsumer3.getCount() );
        
        itsTarget.startDispatching();
        
        itsProducer1.startProducing();
        itsProducer2.startProducing();
        itsProducer3.startProducing();
        
        itsProducer1.stopProducing();
        itsProducer2.stopProducing();
        itsProducer3.stopProducing();
        
        assertEquals( MAX,itsProducer1.getCount() );
        assertEquals( MAX,itsProducer2.getCount() );
        assertEquals( MAX,itsProducer3.getCount() );
              
        assertEquals( MAX,itsConsumer1.getCount() );
        assertEquals( MAX,itsConsumer2.getCount() );
        assertEquals( MAX,itsConsumer3.getCount() );
    }
    
    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#dispatch(java.lang.Object)}.
     * @throws InterruptedException 
     */
    @Test
    public void 
    testDispatch2() throws InterruptedException
    {
        ICountRequestConsumer consumer = 
            new CountRequestConsumer(3);
        
        itsTarget.attachConsumer( consumer );
        
        assertEquals( 0,itsProducer1.getCount() );
        assertEquals( 0,itsProducer2.getCount() );
        assertEquals( 0,itsProducer3.getCount() );
        
        assertEquals( 0,itsConsumer1.getCount() );
        assertEquals( 0,itsConsumer2.getCount() );
        assertEquals( 0,itsConsumer3.getCount() );
        
        itsTarget.startDispatching();
        
        itsProducer1.startProducing();
        itsProducer2.startProducing();
        itsProducer3.startProducing();
        
        itsProducer1.stopProducing();
        itsProducer2.stopProducing();
        itsProducer3.stopProducing();
        
        assertEquals( MAX,itsProducer1.getCount() );
        assertEquals( MAX,itsProducer2.getCount() );
        assertEquals( MAX,itsProducer3.getCount() );
              
        assertEquals( MAX,itsConsumer1.getCount() );
        assertEquals( MAX,itsConsumer2.getCount() );
        assertEquals( MAX,itsConsumer3.getCount()+consumer.getCount() );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#hasConsumer(strata1.common.producerconsumer.IConsumer)}.
     */
    @Test
    public void 
    testHasConsumer()
    {
        ICountRequestConsumer  consumer = new CountRequestConsumer(100);
        
        assertFalse( itsTarget.hasConsumer( consumer ));
        itsTarget.attachConsumer( consumer );
        assertTrue( itsTarget.hasConsumer( consumer )); 
        itsTarget.detachConsumer( consumer );
        assertFalse( itsTarget.hasConsumer( consumer ));
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#startDispatching()}.
     */
    @Test
    public void 
    testStartDispatching()
    {
        assertFalse( itsTarget.isDispatching() );
        itsTarget.startDispatching();
        assertTrue( itsTarget.isDispatching() );       
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#stopDispatching()}.
     */
    @Test
    public void 
    testStopDispatching()
    {
        assertFalse( itsTarget.isDispatching() );
        itsTarget.startDispatching();
        assertTrue( itsTarget.isDispatching() );  
        itsTarget.stopDispatching();
        assertFalse( itsTarget.isDispatching() );
    }

    /**
     * Test method for {@link strata1.common.producerconsumer.DisruptorRouter#isDispatching()}.
     */
    @Test
    public void 
    testIsDispatching()
    {
        assertFalse( itsTarget.isDispatching() );
        itsTarget.startDispatching();
        assertTrue( itsTarget.isDispatching() );  
        itsTarget.stopDispatching();
        assertFalse( itsTarget.isDispatching() );
    }

}

// ##########################################################################
