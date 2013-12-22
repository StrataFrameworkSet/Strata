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

package strata1.common.container;

import static org.junit.Assert.*;
import strata1.common.money.Money;
import strata1.common.utility.ICopyable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import junit.framework.Assert;

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

    /**
     * Test method for {@link IContainer#registerType(ITypeDefinition)}.
     */
    @Test
    public void 
    testRegisterType()
    {
        ITypeDefinition definition = 
            itsTarget
                .createTypeDefinition()
                .setType( ICopyable.class,Money.class )
                .setName( "example" )
                .setLifetime( LifetimeKind.PER_RESOLVE )
                .setConstructorInjector( 
                    itsTarget
                        .createConstructorInjector()
                        .insertConstructorValue( "currency" )
                        .insertConstructorValue( "amount" ) );
                
        Assert.assertFalse( itsTarget.hasType( definition ));
        itsTarget.registerType( definition );
    }

    /**
     * Test method for {@link IContainer#registerInstance(Object)}.
     */
    @Test
    public void 
    testRegisterInstanceT()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#registerInstance(String,Object)}.
     */
    @Test
    public void 
    testRegisterInstanceStringT()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#resolveType(Class)}.
     */
    @Test
    public void 
    testResolveTypeClassOfT()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#resolveType(Class,String)}.
     */
    @Test
    public void 
    testResolveTypeClassOfTString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#resolveInstance(Class)}.
     */
    @Test
    public void 
    testResolveInstanceClassOfT()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#resolveInstance(Class,String)}.
     */
    @Test
    public void 
    testResolveInstanceClassOfTString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#createTypeDefinition()}.
     */
    @Test
    public void 
    testCreateTypeDefinition()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#createConstructorInjector()}.
     */
    @Test
    public void 
    testCreateConstructorInjector()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link IContainer#createPropertyInjector()}.
     */
    @Test
    public void 
    testCreatePropertyInjector()
    {
        fail( "Not yet implemented" );
    }

    protected abstract IContainer
    createContainer();
}

// ##########################################################################
