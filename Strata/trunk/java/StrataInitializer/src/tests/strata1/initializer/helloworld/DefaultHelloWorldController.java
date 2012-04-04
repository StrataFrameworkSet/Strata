// ##########################################################################
// # File Name:	DefaultHelloWorldController.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.helloworld;

import strata1.interactor.controller.AbstractController;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DefaultHelloWorldController
    extends    AbstractController
    implements HelloWorldController
{
    private HelloWorldModel itsModel;
    private HelloWorldView  itsView;
    
    /************************************************************************
     * Creates a new DefaultHelloWorldController. 
     *
     */
    public 
    DefaultHelloWorldController(HelloWorldModel model,HelloWorldView view)
    {
        itsModel = model;
        itsView  = view;
       
        itsModel.setProcessor( this );
        itsView.setProvider( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        itsView.start();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasCommand(String commandName)
    {
        return false;
    }

}


// ##########################################################################
