// ##########################################################################
// # File Name:	GuiceContainerTest.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjectorTest Framework.
// #
// #   			The StrataInjectorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjectorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjectorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.injector.guicecontainer;

import static org.junit.Assert.*;
import strata1.injector.container.ContainerTest;
import strata1.injector.container.IContainer;
import com.google.inject.TypeLiteral;
import org.junit.Test;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceContainerTest
    extends ContainerTest
{

    /************************************************************************
     * Creates a new {@code GuiceContainerTest}. 
     *
     */
    public 
    GuiceContainerTest()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IContainer 
    createContainer()
    {
        return new GuiceContainer();
    }

    @Test
    public void
    testGuiceTypeLiteral()
    {
        TypeLiteral<String>        t1 = TypeLiteral.get( String.class );
        TypeLiteral<List<String>>  t2 = new TypeLiteral<List<String>>() {};
        TypeLiteral<List<Integer>> t3 = new TypeLiteral<List<Integer>>() {};
        
        assertEquals( String.class,t1.getRawType() );
        assertEquals( List.class,t2.getRawType() );
        assertEquals( String.class,t1.getType() );
        assertEquals( t3.getType(),t2.getType() );
    }
}

// ##########################################################################
