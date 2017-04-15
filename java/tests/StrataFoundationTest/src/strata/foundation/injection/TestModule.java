// ##########################################################################
// # File Name:	TestModule.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundationTest Framework.
// #
// #   			The StrataFoundationTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundationTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundationTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.injection;

import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.SingletonScope;
import strata.foundation.injection.ThreadScope;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TestModule 
    extends AbstractModule
{

    /************************************************************************
     * Creates a new TestModule. 
     *
     * @param name
     */
    public 
    TestModule()
    {
        super( "TestModule" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initialize()
    {
        bindType( String.class )
            .withKey( "test1" )
            .toInstance( "XXXXXXX" );
        
        bindType( String.class )
            .withKey( "test2" )
            .toInstance( "YYYYYYY" );
        
        bindType( Integer.class ).toInstance( new Integer(12345) );
        
        bindType( IFoo.class )
            .toType( Foo.class )
            .withScope( getDefaultScope() );
        
        bindType( IBar.class )
            .toType( Bar.class )
            .withScope( ThreadScope.class );
        
        bindType( IFooBar.class )
            .toType( FooBar.class )
            .withScope( SingletonScope.class );        
    }

}

// ##########################################################################
