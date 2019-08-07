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

package strata.foundation.injection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
public abstract
class ContainerTest
{
    private IContainer itsTarget;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = createContainer(new TestModule());
    }

    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
    }

    @Test
    public void
    testGetInstance()
    {
        IFooBar foobar = null;
                
        foobar = itsTarget.getInstance( IFooBar.class );
        assertNotNull( foobar );
        assertNotNull( foobar.getFoo() );
        assertEquals( "XXXXXXX",foobar.getFoo().getFooName() );
        assertNotNull( foobar.getBar() );
        assertEquals( 12345,foobar.getBar().getBarId() );
        assertEquals( "YYYYYYY",foobar.getBaz() );
        
    }
    
    @Test
    public void
    testGetInstanceOfSelfInjectedContainer()
    {
        IContainer container = itsTarget.getInstance( IContainer.class );
        
        assertNotNull( container );
        assertEquals( itsTarget,container );
    }
    
    protected abstract IContainer
    createContainer(IModule... modules);
}

// ##########################################################################
