// ##########################################################################
// # File Name:	ConstructorBasedProviderTest.java
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

package strata1.injector.container;

import static org.junit.Assert.assertNotNull;
import strata1.injector.jrereflection.JreConstructor;
import strata1.injector.jrereflection.JreTypeManager;
import strata1.injector.reflection.IConstructor;
import strata1.injector.reflection.IType;
import strata1.injector.reflection.ITypeManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Constructor;
import javax.inject.Inject;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ConstructorBasedProviderTest
{
    private IContainer                        itsContainer;
    private ConstructorBasedProvider<IFooBar> itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsContainer = new Container(new JreTypeManager());
        
        itsContainer
            .insertBinding( 
                Binder
                    .bindType( String.class )
                    .withKey( "test1" )
                    .toInstance( "XXXXXXX" ) )
            .insertBinding( 
                Binder
                    .bindType( Integer.class )
                    .toInstance( 12345 ) )
            .insertBinding( 
                Binder
                    .bindType( IFoo.class )
                    .toType( Foo.class ) )
            .insertBinding( 
                Binder
                    .bindType( IBar.class )
                    .toType( Bar.class ) );
        
        itsTarget = 
            new ConstructorBasedProvider<IFooBar>(
                itsContainer,
                itsContainer.getTypeManager().getType( FooBar.class ),
                getInjectionConstructor(FooBar.class));
        
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
        itsContainer = null;
    }

    /**
     * Test method for {@link strata1.injector.container.ConstructorBasedProvider#get()}.
     */
    @Test
    public void 
    testGet()
    {
        IFooBar actual = itsTarget.get();
        
        assertNotNull( actual );
        assertNotNull( actual.getFoo() );
        assertNotNull( actual.getBar() );
    }

    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    private <T> IConstructor<? extends T> 
    getInjectionConstructor(Class<? extends T> type)
    {
        ITypeManager       manager = new JreTypeManager();
        IType<? extends T> wrapper = manager.getType( type );
        
        if ( wrapper.hasConstructor( Inject.class ) )
            return wrapper.getConstructor( Inject.class );
        
        return wrapper.getDefaultConstructor();        
    }

}

// ##########################################################################
