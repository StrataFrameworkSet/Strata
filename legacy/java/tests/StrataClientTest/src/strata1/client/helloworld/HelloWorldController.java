// ##########################################################################
// # File Name:	HelloWorldController.java
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

package strata1.client.helloworld;

import strata1.client.command.ICommand;
import strata1.client.controller.AbstractController;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HelloWorldController
    extends    
        AbstractController<
            IHelloWorldProvider,
            IHelloWorldView,
            IHelloWorldModel>
    implements 
        IHelloWorldController
{    
    /************************************************************************
     * Creates a new HelloWorldController. 
     *
     */
    public 
    HelloWorldController(IHelloWorldModel model,IHelloWorldView view)
    {
        setModel( model );
        setView( view,this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommand 
    getExitCommand()
    {
        return 
            new ICommand() 
            {
                public void 
                execute() 
                {
                    getView().stop();
                    //System.exit( 0 );
                }
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        getView().setGreeting( "Hello John!" );
        getView().start();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
    }


}


// ##########################################################################
