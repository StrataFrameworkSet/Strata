// ##########################################################################
// # File Name:	ClientBootstrapperTest.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.bootstrap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import strata1.client.region.IRegionManager;
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
class ClientBootstrapperTest
{
    private IClientBootstrapper itsTarget;
    private IClientFactory      itsFactory;
    
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
        itsTarget  = createClientBootstrapper();
        itsFactory = new MockClientFactory();
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
        itsFactory = null;
        itsTarget  = null;
    }

    /**
     * Test method for {@link IClientBootstrapper#setModuleManager(IClientModuleManager)}.
     */
    @Test
    public void 
    testSetGetModuleManager()
    {
        IClientModuleManager expected = itsFactory.createModuleManager();
        
        itsTarget.setModuleManager( expected );
        assertEquals( expected,itsTarget.getModuleManager() );
    }

    /**
     * Test method for {@link IClientBootstrapper#setContainer(IClientContainer)}.
     */
    @Test
    public void 
    testSetGetContainer()
    {
        IClientContainer expected = itsFactory.createContainer();
        
        itsTarget.setContainer( expected );
        assertEquals( expected,itsTarget.getContainer() );
    }

    /**
     * Test method for {@link IClientBootstrapper#setRegionManager(IRegionManager)}.
     */
    @Test
    public void 
    testSetGetRegionManager()
    {
        IRegionManager expected = itsFactory.createRegionManager();
        
        itsTarget.setRegionManager( expected );
        assertEquals( expected,itsTarget.getRegionManager() );
    }

    /**
     * Test method for {@link IClientBootstrapper#run(IClientFactory)}.
     */
    @Test
    public void 
    testRun()
    {
        itsTarget.run( itsFactory );
        
        verify(itsTarget.getModuleManager())
            .initialize( itsTarget );
        
        for (IClientModule module:itsTarget.getModuleManager())
            verify(module)
                .initialize( itsTarget );
        

    }
    
    protected IClientBootstrapper
    createClientBootstrapper()
    {
        return new MockClientBootstrapper();
    }
}

// ##########################################################################
