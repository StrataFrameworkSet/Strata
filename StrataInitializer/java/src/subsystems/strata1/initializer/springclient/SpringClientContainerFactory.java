// ##########################################################################
// # File Name:	SpringClientContainerFactory.java
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

package strata1.initializer.springclient;

import strata1.initializer.client.ClientContainer;
import strata1.initializer.client.ClientContainerFactory;
import strata1.initializer.client.DefaultClientContainer;
import strata1.initializer.springprovider.SpringContainerProvider;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringClientContainerFactory
    implements ClientContainerFactory
{

    /************************************************************************
     * Creates a new SpringClientContainerFactory. 
     *
     */
    public 
    SpringClientContainerFactory() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ClientContainer 
    createClientContainer()
    {
        return 
            new DefaultClientContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext()));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ClientContainer 
    createClientContainer(String resourceLocation)
    {
        return 
            new DefaultClientContainer(
                new SpringContainerProvider(
                    new GenericXmlApplicationContext(resourceLocation)));
    }

}


// ##########################################################################
