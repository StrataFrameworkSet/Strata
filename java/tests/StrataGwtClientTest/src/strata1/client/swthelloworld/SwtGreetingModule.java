// ##########################################################################
// # File Name:	SwtGreetingModule.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.client.swthelloworld;

import strata1.client.helloworld.GreetingModule;
import strata1.client.helloworld.IHelloWorldView;
import strata1.client.shell.IDispatcher;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtGreetingModule
    extends GreetingModule
{

    /************************************************************************
     * Creates a new {@code SwtGreetingModule}. 
     *
     */
    public 
    SwtGreetingModule()
    {
        super("SwtGreetingModule");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected IHelloWorldView 
    createHelloWorldView(IDispatcher dispatcher)
    {
        return new SwtHelloWorldView(dispatcher);
    }

}

// ##########################################################################
