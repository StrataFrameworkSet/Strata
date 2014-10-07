// ##########################################################################
// # File Name:	ObjectMessageTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegratorTest Framework.
// #
// #   			The StrataIntegratorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegratorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegratorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.messaging;

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
public abstract 
class ObjectMessageTest
    extends MessageTest
{
    private IObjectMessage itsTarget;
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Before
    public void 
    setUp() throws Exception
    {
        super.setUp();
        itsTarget = getTarget();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
        super.tearDown();
    }

    /**
     * Test method for {@link IObjectMessage#setPayload(Serializable)}.
     * Test method for {@link IObjectMessage#getPayload()}.
     */
    @Test
    public void 
    testSetGetPayload()
    {
        Foo expected = new Foo(3,5.7,"zzzzzzzz");
        
        itsTarget.setPayload( expected );
        assertEquals( expected,itsTarget.getPayload() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected abstract IObjectMessage 
    getTarget();

}

// ##########################################################################
