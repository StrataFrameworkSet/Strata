// ##########################################################################
// # File Name:	PropertiesModuleTest.java
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

package strata1.injector.property;

import static org.junit.Assert.*;
import strata1.injector.container.Container;
import strata1.injector.container.IContainer;
import strata1.injector.jrereflection.JreTypeManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.InputStream;
import java.util.Properties;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PropertiesModuleTest
{
    private PropertiesModule itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        Properties  properties = new Properties();
        InputStream input = 
            ClassLoader
                .getSystemClassLoader()
                .getResourceAsStream( 
                    "strata1/injector/property/propertiesmoduletest.properties" );
        
        if ( input == null )
            throw new NullPointerException( "input is null" );
        
        properties.load( input );
        itsTarget = new PropertiesModule( properties );
    }

    /************************************************************************
     *  
     *
     * @throws Exception
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
    }

    /**
     * Test method for {@link PropertiesModule#initialize(IContainer)}.
     */
    @Test
    public void 
    testInitialize() throws Exception
    {
        IContainer container = new Container(new JreTypeManager());
        
        itsTarget.initialize( container );
        assertTrue( container.hasBinding( String.class,"FOO" ) );
        assertEquals(
            "FOO_VALUE",
            container.getInstance( String.class,"FOO" ));
    }

}

// ##########################################################################
