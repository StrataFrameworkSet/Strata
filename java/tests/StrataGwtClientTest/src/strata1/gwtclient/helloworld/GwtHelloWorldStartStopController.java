// ##########################################################################
// # File Name:	GwtHelloWorldStartStopController.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtClientTest Framework.
// #
// #   			The StrataGwtClientTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtClientTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtClientTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.helloworld;

import strata1.injector.bootstrap.IStartStopController;
import strata1.injector.container.IContainer;
import strata1.client.controller.ILoginController;
import strata1.client.helloworld.IHelloWorldController;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtHelloWorldStartStopController
    implements IStartStopController
{
    private IContainer itsContainer;
    
    /************************************************************************
     * Creates a new {@code GwtHelloWorldStartStopController}. 
     *
     */
    public 
    GwtHelloWorldStartStopController() 
    {
        itsContainer = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IStartStopController 
    setContainer(IContainer container)
    {
        itsContainer = container;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startApplication()
    {
        ILoginController controller = 
            itsContainer.getInstance(ILoginController.class);
        
        if ( controller == null )
            throw new NullPointerException("Controller is null.");

        controller.start();   
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopApplication()
    {
        IHelloWorldController controller = 
            itsContainer.getInstance(IHelloWorldController.class);
        
        if ( controller == null )
            throw new NullPointerException("Controller is null.");

        controller.stop();   
    }

}

// ##########################################################################
