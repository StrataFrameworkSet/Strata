// ##########################################################################
// # File Name:	AbstractBaseContainer.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.base;

import strata1.common.annotation.Factory;
import strata1.initializer.provider.ContainerProvider;
import strata1.integrator.annotation.Gateway;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractBaseContainer
    implements BaseContainer
{
    private ContainerProvider itsProvider;

    /************************************************************************
     * Creates a new AbstractBaseContainer. 
     *
     */
    public 
    AbstractBaseContainer(ContainerProvider provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <G> G 
    getGateway(Class<G> gatewayType,String name)
    {
        if ( !gatewayType.isAnnotationPresent( Gateway.class ) )
            throw 
                new IllegalArgumentException( 
                    gatewayType + "is not a gateway.");
        
        return itsProvider.getInstance( gatewayType,name );
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
     *  
     *
     * @return
     */
    protected ContainerProvider
    getProvider()
    {
        return itsProvider;
    }
}


// ##########################################################################
