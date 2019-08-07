// ##########################################################################
// # File Name:	HelloWorldStartStopController.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSwtClientTest Framework.
// #
// #   			The StrataSwtClientTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSwtClientTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSwtClientTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.swthelloworld;

import strata1.client.helloworld.IHelloWorldController;
import javax.inject.Inject;
import strata.foundation.bootstrap.IStartStopController;
import strata.foundation.injection.IContainer;
import strata.presentation.login.ILoginController;
import strata.presentation.shell.IDispatcher;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HelloWorldStartStopController
    implements IStartStopController
{
    private final IContainer itsContainer;
    
    /************************************************************************
     * Creates a new {@code HelloWorldStartStopController}. 
     *
     */
    @Inject
    public 
    HelloWorldStartStopController(IContainer container)
    {
        itsContainer = container;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startApplication()
    {
        IDispatcher dispatcher = 
            itsContainer.getInstance( IDispatcher.class );
        
        Runnable startUpTask = 
            new Runnable()
            {
                public void
                run()
                {   
                    try
                    {
                        doStartUp();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace(System.out);
                        System.exit( -1 );
                    }
                }
            };

        dispatcher.insertTask( startUpTask );
        dispatcher.start();
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
        System.exit( 0 );
    }

    /************************************************************************
     *  
     *
     */
    private void
    doStartUp()
    {  
        ILoginController controller = 
            itsContainer.getInstance(ILoginController.class);
        
        if ( controller == null )
            throw new NullPointerException("Controller is null.");

        controller.start();   
    }

}

// ##########################################################################
