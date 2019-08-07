// ##########################################################################
// # File Name:	StandardContainerTest.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundationTest Framework.
// #
// #   			The StrataFoundationTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundationTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundationTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.guiceinjection;

import strata.foundation.injection.ContainerTest;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IModule;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceContainerTest 
    extends ContainerTest
{

    /************************************************************************
     * Creates a new StandardContainerTest. 
     *
     */
    public 
    GuiceContainerTest()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IContainer 
    createContainer(IModule...modules)
    {
        return new GuiceContainer( modules );
    }

}

// ##########################################################################
