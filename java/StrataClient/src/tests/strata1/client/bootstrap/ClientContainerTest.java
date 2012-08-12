// ##########################################################################
// # File Name:	ClientContainerTest.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.bootstrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import strata1.interactor.view.IView;
import strata1.interactor.view.IViewContainer;
import strata1.common.springcontainerprovider.SpringContainerProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.support.GenericApplicationContext;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ClientContainerTest
{
    private IClientContainer itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = 
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext()));
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
     * Test method for {@link IViewContainer#registerView(IView)}.
     */
    @Test
    public void 
    testRegisterViewV()
    {
        IView view = Mockito.mock( IView.class );
        
        assertFalse( itsTarget.hasView( view.getClass() ) );
        itsTarget.registerView( view );
        assertTrue( itsTarget.hasView( view.getClass() ) );
        assertEquals( view,itsTarget.getView( view.getClass() ) );
    }

    /**
     * Test method for {@link strata1.interactor.view.IViewContainer#registerView(java.lang.String, strata1.interactor.view.IView)}.
     */
    @Test
    public void testRegisterViewStringV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.view.IViewContainer#getView(java.lang.Class)}.
     */
    @Test
    public void testGetViewClassOfV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.view.IViewContainer#getView(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetViewClassOfVString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.view.IViewContainer#hasView(java.lang.Class)}.
     */
    @Test
    public void testHasViewClassOfV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.view.IViewContainer#hasView(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasViewClassOfVString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#registerViewModel(strata1.interactor.viewmodel.IViewModel)}.
     */
    @Test
    public void testRegisterViewModelV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#registerViewModel(java.lang.String, strata1.interactor.viewmodel.IViewModel)}.
     */
    @Test
    public void testRegisterViewModelStringV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#getViewModel(java.lang.Class)}.
     */
    @Test
    public void testGetViewModelClassOfV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#getViewModel(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetViewModelClassOfVString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#hasViewModel(java.lang.Class)}.
     */
    @Test
    public void testHasViewModelClassOfV()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.viewmodel.IViewModelContainer#hasViewModel(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasViewModelClassOfVString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#registerController(strata1.interactor.controller.IController)}.
     */
    @Test
    public void testRegisterControllerC()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#registerController(java.lang.String, strata1.interactor.controller.IController)}.
     */
    @Test
    public void testRegisterControllerStringC()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#getController(java.lang.Class)}.
     */
    @Test
    public void testGetControllerClassOfC()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#getController(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetControllerClassOfCString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#hasController(java.lang.Class)}.
     */
    @Test
    public void testHasControllerClassOfC()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.controller.IControllerContainer#hasController(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasControllerClassOfCString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#registerModel(strata1.interactor.model.IModel)}.
     */
    @Test
    public void testRegisterModelM()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#registerModel(java.lang.String, strata1.interactor.model.IModel)}.
     */
    @Test
    public void testRegisterModelStringM()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#getModel(java.lang.Class)}.
     */
    @Test
    public void testGetModelClassOfM()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#getModel(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetModelClassOfMString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#hasModel(java.lang.Class)}.
     */
    @Test
    public void testHasModelClassOfM()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.interactor.model.IModelContainer#hasModel(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasModelClassOfMString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#registerGateway(java.lang.Object)}.
     */
    @Test
    public void testRegisterGatewayG()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#registerGateway(java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testRegisterGatewayStringG()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#getGateway(java.lang.Class)}.
     */
    @Test
    public void testGetGatewayClassOfG()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#getGateway(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetGatewayClassOfGString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#hasGateway(java.lang.Class)}.
     */
    @Test
    public void testHasGatewayClassOfG()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.integrator.gateway.IGatewayContainer#hasGateway(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasGatewayClassOfGString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#registerFactory(java.lang.Object)}.
     */
    @Test
    public void testRegisterFactoryF()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#registerFactory(java.lang.String, java.lang.Object)}.
     */
    @Test
    public void testRegisterFactoryStringF()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#getFactory(java.lang.Class)}.
     */
    @Test
    public void testGetFactoryClassOfF()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#getFactory(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testGetFactoryClassOfFString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#hasFactory(java.lang.Class)}.
     */
    @Test
    public void testHasFactoryClassOfF()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata1.common.factory.IFactoryContainer#hasFactory(java.lang.Class, java.lang.String)}.
     */
    @Test
    public void testHasFactoryClassOfFString()
    {
        fail( "Not yet implemented" );
    }

}

// ##########################################################################
