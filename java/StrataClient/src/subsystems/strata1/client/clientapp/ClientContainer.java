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

package strata1.client.clientapp;

import strata1.initializer.provider.IContainerProvider;
import strata1.interactor.controller.IController;
import strata1.interactor.model.IModel;
import strata1.interactor.view.IView;
import strata1.interactor.viewmodel.IViewModel;
import strata1.client.base.AbstractBaseContainer;
import strata1.client.base.InstanceInserter;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class ClientContainer
    extends    AbstractBaseContainer
    implements IClientContainer
{
    /************************************************************************
     * Creates a new ClientContainer. 
     *
     */
    public 
    ClientContainer(IContainerProvider provider)
    {
        super( provider );
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


}


// ##########################################################################
