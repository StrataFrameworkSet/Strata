// ##########################################################################
// # File Name:	SpringServerContainerFactory.java
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

package strata1.server.springserver;

import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import strata1.common.springcontainerprovider.SpringContainerProvider;
import strata1.server.bootstrap.IServerContainer;
import strata1.server.bootstrap.ServerContainer;

/**
 * 
 * @author 		
 *     Architecture Strategy Group 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SpringServerContainerFactory
{

    /************************************************************************
     * Creates a new SpringServerContainerFactory. 
     *
     */
    public 
    SpringServerContainerFactory() {}

    /************************************************************************
     * {@inheritDoc} 
     */
    public IServerContainer 
    createServerContainer()
    {
        return
            new ServerContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext()));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    public IServerContainer 
    createServerContainer(String resourceLocation)
    {
        return
            new ServerContainer(
                new SpringContainerProvider(
                    new GenericXmlApplicationContext(resourceLocation)));
    }

}


// ##########################################################################
