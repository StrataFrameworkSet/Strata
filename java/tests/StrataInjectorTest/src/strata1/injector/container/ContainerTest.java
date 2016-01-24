// ##########################################################################
// # File Name:	ContainerTest.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.container;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import strata1.injector.jrereflection.JreTypeManager;
import strata1.common.logger.ILogger;
import strata1.common.logger.Logger;
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
class ContainerTest
{
    private IContainer itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = createContainer();
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


    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testInsertBinding1()
    {
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .toProvider( LoggerProvider.class ) );
        
        ILogger logger = itsTarget.getInstance( ILogger.class );
        
        assertNotNull( logger );
                
        logger.logInfo( "Test Message 1" );
    }
    
    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testInsertBinding2()
    {
        ILogger logger1 = null;
        ILogger logger2 = null;
        
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .toProvider( LoggerProvider.class )
                    .withScope( new SingletonScope<ILogger>() ));
        
        logger1 = itsTarget.getInstance( ILogger.class );
        logger2 = itsTarget.getInstance( ILogger.class );
        
        assertNotNull( logger1 );
        assertSame( logger1,logger2 );
                
        logger1.logInfo( "Test Message 2" );
    }
    
    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testInsertBinding3()
    {
        ILogger logger1 = null;
        ILogger logger2 = null;
        
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .toProvider( LoggerProvider.class )
                    .withScope( new ThreadScope<ILogger>() ));
        
        logger1 = itsTarget.getInstance( ILogger.class );
        logger2 = itsTarget.getInstance( ILogger.class );
        
        assertNotNull( logger1 );
        assertSame( logger1,logger2 );
                
        logger1.logInfo( "Test Message 3" );
    }
    
    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testInsertBinding4()
    {
        IFooBar foobar = null;
        
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( String.class )
                    .withKey( "test1" )
                    .toInstance( "XXXXXXX" ) )
            .insertBinding( 
                Binder
                    .bindType( String.class )
                    .withKey( "test2" )
                    .toInstance( "YYYYYYY" ) )
            .insertBinding( 
                Binder
                    .bindType( Integer.class )
                    .toInstance( new Integer(12345) ) )
            .insertBinding( 
                Binder
                    .bindType( IFoo.class )
                    .toType( Foo.class ) )
            .insertBinding( 
                Binder
                    .bindType( IBar.class )
                    .toType( Bar.class ) )
            .insertBinding( 
                Binder
                    .bindType( IFooBar.class )
                    .toType( FooBar.class ) );
        
        foobar = itsTarget.getInstance( IFooBar.class );
        assertNotNull( foobar );
        assertNotNull( foobar.getFoo() );
        assertEquals( "XXXXXXX",foobar.getFoo().getFooName() );
        assertNotNull( foobar.getBar() );
        assertEquals( 12345,foobar.getBar().getBarId() );
        assertEquals( "YYYYYYY",foobar.getBaz() );
        
    }
    
    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testInsertBinding5()
    {
        ILogger logger = null;
        
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .withKey( MainLogger.class )
                    .toProvider( new LoggerProvider() ) );
        
        logger = 
            itsTarget.getInstance( ILogger.class,MainLogger.class );
        
        assertNotNull( logger );
        logger.logInfo( "Test Message 5" );
        
        logger = 
            itsTarget.getInstance( ILogger.class );
        
        assertNull( logger );
    }
    
    /************************************************************************
     *  
     *
     */
    @Test
    public void
    testBindType()
    {
        itsTarget
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .withKey( "MainLogger" )
                    .toType( Logger.class )
                    .withScope( new SingletonScope<ILogger>() ) )
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .withKey( MainLogger.class )
                    .toInstance( new Logger() ) )
            .insertBinding( 
                Binder
                    .bindType( ILogger.class )
                    .toProvider( LoggerProvider.class ) );
        
        ILogger logger = itsTarget.getInstance( ILogger.class );
    }

    protected IContainer
    createContainer()
    {
        return new Container(new JreTypeManager());
    }
}

// ##########################################################################
