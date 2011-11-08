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

import strata1.common.annotation.Factory;
import strata1.initializer.base.AbstractBaseContainer;
import strata1.initializer.provider.ContainerProvider;
import strata1.integrator.annotation.Gateway;
import strata1.interactor.view.View;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class DefaultClientContainer
    extends    AbstractBaseContainer
    implements ClientContainer
{

    /************************************************************************
     * Creates a new DefaultClientContainer. 
     *
     */
    public 
    DefaultClientContainer(ContainerProvider provider)
    {
        super( provider );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V extends View>V 
    getView(Class<V> viewType,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <V>V 
    getViewModel(Class<V> viewModelType,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <C>C 
    getController(Class<C> controllerType,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <M>M 
    getModel(Class<M> modelType,String name)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected boolean
    isSupportedType(Class<?> type)
    {
        return
            type.isAnnotationPresent( Gateway.class ) ||
            type.isAnnotationPresent( Factory.class ) ||
            (View.class).isAssignableFrom( type );
    }

}


// ##########################################################################
