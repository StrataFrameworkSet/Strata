// ##########################################################################
// # File Name:	ClientApplication.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.application;

import strata1.client.bootstrap.IClientBootstrapper;
import strata1.client.bootstrap.IClientFactory;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ClientApplication
    implements IClientApplication
{
    private IClientBootstrapper   itsBootstrapper;
    private IClientFactory        itsFactory;
    
    /************************************************************************
     * Creates a new {@code ClientApplication}. 
     *
     */
    public 
    ClientApplication(
        IClientBootstrapper bootstrapper,
        IClientFactory      factory)
    {
        itsBootstrapper = bootstrapper;
        itsFactory      = factory;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(String[] args)
    {
        itsBootstrapper.run( itsFactory,args );
    }

}

// ##########################################################################
