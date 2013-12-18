// ##########################################################################
// # File Name:	SessionService.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtInteractor Framework.
// #
// #   			The StrataGwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.sessionserver;

import strata1.gwtclient.sessionclient.ISessionService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SessionService
    extends RemoteServiceServlet
    implements ISessionService
{

    private static final long serialVersionUID = -2347859087811625339L;

    /************************************************************************
     * Creates a new {@code SessionService}. 
     *
     */
    public 
    SessionService()
    {
    }

    /************************************************************************
     * Creates a new {@code SessionService}. 
     *
     * @param delegate
     */
    public 
    SessionService(Object delegate)
    {
        super( delegate );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startSession()
    {
        // TODO Auto-generated method stub
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopSession()
    {
    }
}

// ##########################################################################
