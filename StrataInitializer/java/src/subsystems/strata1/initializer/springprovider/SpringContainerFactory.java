// ##########################################################################
// # File Name:	SpringContainerFactory.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.springprovider;

import strata1.initializer.provider.AbstractContainerFactory;
import strata1.initializer.provider.ContainerProvider;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class SpringContainerFactory
    extends AbstractContainerFactory
{

    /************************************************************************
     * Creates a new {@code SpringContainerFactory}. 
     *
     */
    public 
    SpringContainerFactory() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ContainerProvider 
    createComponentContainer()
    {
        
        return 
            new SpringContainerProvider(new GenericApplicationContext());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ContainerProvider 
    createComponentContainer(String resourceLocation)
    {
        return 
            new SpringContainerProvider(
                new GenericXmlApplicationContext(resourceLocation));
    }

}

// ##########################################################################
