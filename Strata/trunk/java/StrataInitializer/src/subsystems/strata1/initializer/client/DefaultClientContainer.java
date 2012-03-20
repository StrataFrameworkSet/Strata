// ##########################################################################
// # File Name:	DefaultClientContainer.java
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

package strata1.initializer.client;

import strata1.initializer.base.AbstractBaseContainer;
import strata1.initializer.provider.IContainerProvider;
import strata1.interactor.controller.IController;
import strata1.interactor.model.IModel;
import strata1.interactor.view.IView;
import strata1.interactor.viewmodel.IViewModel;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class DefaultClientContainer
    extends    AbstractBaseContainer
    implements IClientContainer
{

    /************************************************************************
     * Creates a new DefaultClientContainer. 
     *
     */
    public 
    DefaultClientContainer(IContainerProvider provider)
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
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> void 
    registerView(String viewName,V view)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView>V 
    getView(Class<V> viewType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> boolean 
    hasView(Class<V> viewType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> boolean 
    hasView(Class<V> viewType,String viewName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IView> V 
    getView(Class<V> viewType,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> void 
    registerViewModel(V viewmodel)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> void 
    registerViewModel(String viewmodelName,V viewmodel)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> V 
    getViewModel(Class<V> viewmodelType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> V 
    getViewModel(Class<V> viewmodelType,String viewmodelName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> boolean 
    hasViewModel(Class<V> viewmodelType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends IViewModel> boolean 
    hasViewModel(Class<V> viewmodelType,String viewmodelName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> void 
    registerController(C controller)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> void 
    registerController(String controllerName,C controller)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> C 
    getController(Class<C> controllerType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> C 
    getController(Class<C> controllerType,String controllerName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> boolean 
    hasController(Class<C> controllerType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C extends IController> boolean 
    hasController(Class<C> controllerType,String controllerName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> void 
    registerModel(M model)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> void 
    registerModel(String modelName,M model)
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> M 
    getModel(Class<M> modelType)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> M 
    getModel(Class<M> modelType,String modelName)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> boolean 
    hasModel(Class<M> modelType)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M extends IModel> boolean 
    hasModel(Class<M> modelType,String modelName)
    {
        // TODO Auto-generated method stub
        return false;
    }


}


// ##########################################################################
