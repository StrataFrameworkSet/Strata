// ##########################################################################
// # File Name:	SwtHelloWorldApp.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSwtClient Framework.
// #
// #   			The StrataSwtClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSwtClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSwtClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.swthelloworld;

import strata1.client.application.DesktopApplication;
import strata.foundation.bootstrap.Bootstrapper;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class SwtHelloWorldApp
    extends DesktopApplication
{

    /************************************************************************
     * Creates a new {@code SwtHelloWorldApp}. 
     *
     * @param bootstrapper
     * @param factory
     */
    public 
    SwtHelloWorldApp()
    {
        super( new Bootstrapper(),new HelloWorldFactory() );
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
            new SwtHelloWorldApp().run( args );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}

// ##########################################################################
