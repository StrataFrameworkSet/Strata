// ##########################################################################
// # File Name:	GwtHelloWorldApp.java
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

import strata1.injector.bootstrap.Bootstrapper;
import strata1.injector.bootstrap.IApplicationFactory;
import strata1.injector.bootstrap.IBootstrapper;
import com.google.gwt.core.client.EntryPoint;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GwtHelloWorldApp
    implements EntryPoint
{
    private final IBootstrapper       itsBootstrapper;
    private final IApplicationFactory itsFactory;
    
    /************************************************************************
     * Creates a new {@code GwtHelloWorldApp}. 
     *
     */
    public 
    GwtHelloWorldApp()
    {
        itsBootstrapper = new Bootstrapper();
        itsFactory      = new GwtHelloWorldFactory();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onModuleLoad()
    {
        itsBootstrapper.run( itsFactory );
        itsBootstrapper
            .getStartStopController()
            .startApplication();
    }

}

// ##########################################################################
