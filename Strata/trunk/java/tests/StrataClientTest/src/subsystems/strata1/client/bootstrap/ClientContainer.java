// ##########################################################################
// # File Name:	ClientContainer.java
// #
// # Copyright:	2011, Stratagema Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the Strata Analyzer Framework.
// #
// #   			The Strata Analyzer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The Strata Analyzer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the Strata Analyzer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// #
// #			You can obtain support for the Strata Analyzer 
// #			Framework at http://www.stratagemasystems.com/strata/.
// ##########################################################################

package strata1.client.bootstrap;

import strata1.integrator.annotation.Gateway;
import strata1.common.annotation.Factory;
import strata1.common.authentication.IPrincipal;
import strata1.common.containerprovider.IContainerProvider;
import strata1.common.containerprovider.InstanceInserter;
import strata1.client.controller.IController;
import strata1.client.model.IModel;
import strata1.client.view.IView;
import strata1.client.viewmodel.IViewModel;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ClientContainer
    implements IClientContainer
{
    private IContainerProvider itsProvider;

    /************************************************************************
     * Creates a new ClientContainer. 
     *
     */
    public 
    ClientContainer(IContainerProvider provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> void 
    registerView(V view)
    {
        registerView(view.getClass().getCanonicalName(),view);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> void 
    registerView(String viewName,V view)
    {
        new InstanceInserter<V>(getProvider())
            .insertByType( IView.class,viewName,view );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> V 
    getView(Class<V> viewType)
    {
        return getView( viewType,viewType.getCanonicalName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> V 
    getView(Class<V> viewType,String viewName)
    {
        return getProvider().getInstance( viewType,viewName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> boolean 
    hasView(Class<V> viewType)
    {
        return hasView( viewType,viewType.getCanonicalName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> boolean 
    hasView(Class<V> viewType,String viewName)
    {
        return getProvider().hasInstance( viewType,viewName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> void 
    registerViewModel(V viewmodel)
    {
        registerViewModel(viewmodel.getClass().getCanonicalName(),viewmodel);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> void 
    registerViewModel(String viewmodelName,V viewmodel)
    {
        new InstanceInserter<V>(getProvider())
            .insertByType( IViewModel.class,viewmodelName,viewmodel );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> V 
    getViewModel(Class<V> viewmodelType)
    {
        return getViewModel(viewmodelType,viewmodelType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> V 
    getViewModel(Class<V> viewmodelType,String viewmodelName)
    {
        return getProvider().getInstance( viewmodelType,viewmodelName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> boolean 
    hasViewModel(Class<V> viewmodelType)
    {
        return hasViewModel(viewmodelType,viewmodelType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> boolean 
    hasViewModel(Class<V> viewmodelType,String viewmodelName)
    {
        return getProvider().hasInstance( viewmodelType,viewmodelName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> void 
    registerController(C controller)
    {
        registerController(
            controller.getClass().getCanonicalName(),
            controller);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> void 
    registerController(String controllerName,C controller)
    {
        new InstanceInserter<C>(getProvider())
            .insertByType( IController.class,controllerName,controller );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> C 
    getController(Class<C> controllerType)
    {
        return 
            getController(controllerType,controllerType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> C 
    getController(Class<C> controllerType,String controllerName)
    {
        return 
            getProvider()
                .getInstance( controllerType,controllerName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> boolean 
    hasController(Class<C> controllerType)
    {
        return 
            hasController(controllerType,controllerType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> boolean 
    hasController(Class<C> controllerType,String controllerName)
    {
        return 
            getProvider().hasInstance( controllerType,controllerName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> void 
    registerModel(M model)
    {
        registerModel(model.getClass().getCanonicalName(),model);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> void 
    registerModel(String modelName,M model)
    {
        new InstanceInserter<M>(getProvider())
            .insertByType( IModel.class,modelName,model );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> M 
    getModel(Class<M> modelType)
    {
        return getModel( modelType,modelType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> M 
    getModel(Class<M> modelType,String modelName)
    {
        return getProvider().getInstance( modelType,modelName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> boolean 
    hasModel(Class<M> modelType)
    {
        return hasModel(modelType,modelType.getCanonicalName());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> boolean 
    hasModel(Class<M> modelType,String modelName)
    {
        return getProvider().hasInstance( modelType,modelName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G>void registerGateway(G gateway)
    {
        
        registerGateway( gateway.getClass().getCanonicalName(),gateway );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G>void registerGateway(String gatewayName,G gateway)
    {
        Class<?> gatewayType = gateway.getClass();
        
        if ( gatewayType.isAnnotationPresent( Gateway.class ) )
            getProvider().registerInstance( gatewayName,gateway );
        else
            throw 
                new IllegalArgumentException( 
                    gatewayType + " is not a supported type.");    
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G>G getGateway(Class<G> gatewayType)
    {
        return getGateway( gatewayType,gatewayType.getCanonicalName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G>G getGateway(Class<G> gatewayType,String gatewayName)
    {
        if ( !gatewayType.isAnnotationPresent( Gateway.class ) )
            throw 
                new IllegalArgumentException( 
                    gatewayType + "is not a gateway.");
        
        return itsProvider.getInstance( gatewayType,gatewayName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> boolean 
    hasGateway(Class<G> gatewayType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> boolean 
    hasGateway(Class<G> gatewayType,String gatewayName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> void 
    registerFactory(F factory)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> void 
    registerFactory(String factoryName,F factory)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> F 
    getFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> F 
    getFactory(Class<F> factoryType,String name)
    {
        if ( !factoryType.isAnnotationPresent( Factory.class ) )
            throw 
                new IllegalArgumentException( 
                    factoryType + "is not a factory.");
        
        return itsProvider.getInstance( factoryType,name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> boolean 
    hasFactory(Class<F> factoryType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <F> boolean 
    hasFactory(Class<F> factoryType,String factoryName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> void 
    registerPrincipal(P principal)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> void 
    registerPrincipal(String principalName,P principal)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> P 
    getPrincipal(Class<P> principalType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> P 
    getPrincipal(Class<P> principalType,String principalName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> boolean 
    hasPrincipal(Class<P> principalType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <P extends IPrincipal> boolean 
    hasPrincipal(Class<P> principalType,
        String principalName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    protected IContainerProvider 
    getProvider()
    {
        return itsProvider;
    }


}


// ##########################################################################
