// ##########################################################################
// # File Name:	HelloWorldApp.java
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

import strata1.interactor.region.RegionInitializationException;
import strata1.client.bootstrap.IClientBootstrapper;
import strata1.client.bootstrap.IClientFactory;
import strata1.client.swthelloworld.SwtHelloWorldClientBootstrapper;
import strata1.client.swthelloworld.SwtHelloWorldClientFactory;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HelloWorldApp
    implements Runnable
{
    private IClientBootstrapper itsBootstrapper;
    private IClientFactory      itsFactory;
    
    /************************************************************************
     * Creates a new HelloWorldApp. 
     * @throws RegionInitializationException 
     *
     */
    public 
    HelloWorldApp() 
    {
        itsBootstrapper = new SwtHelloWorldClientBootstrapper();
        itsFactory      = new SwtHelloWorldClientFactory();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run()
    {
       itsBootstrapper.run( itsFactory );
    }

    /************************************************************************
     *  
     *
     * @param args
     */
    public static void 
    main(String[] args)
    {
        try
        {
            new HelloWorldApp().run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

// ##########################################################################
