// ##########################################################################
// # File Name:	UpdateService.java
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

package strata1.gwtclient.updateserver;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import strata1.gwtclient.updateclient.IUpdateService;
import strata1.gwtclient.updateclient.UpdateException;
import strata1.gwtclient.updateclient.UpdateRequest;
import strata1.gwtclient.updateclient.UpdateResponse;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UpdateService
    extends    RemoteServiceServlet
    implements IUpdateService,
               IUpdatableManager
{
    private static final long serialVersionUID = 6543310418320565590L;
    private Map<String,BlockingQueue<UpdateResponse>> itsUpdatables;

    /************************************************************************
     * Creates a new {@code UpdateService}. 
     *
     */
    public 
    UpdateService()
    {
        itsUpdatables = 
            Collections.synchronizedMap( 
                new HashMap<String,BlockingQueue<UpdateResponse>>() );
    }

    /************************************************************************
     * Creates a new {@code UpdateService}. 
     *
     * @param delegate
     */
    public 
    UpdateService(Object delegate)
    {
        super( delegate );
        itsUpdatables = 
            Collections.synchronizedMap( 
                new HashMap<String,BlockingQueue<UpdateResponse>>() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public UpdateResponse 
    getUpdate(UpdateRequest request)
        throws UpdateException
    {
        try
        {
            return
                itsUpdatables
                    .get( request.getUpdatableName() )
                    .take();
        }
        catch (Throwable caught)
        {
            throw new UpdateException( caught );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerUpdatable(
        String                    updatableName,
        BlockingQueue<UpdateResponse> updates)
    {
        itsUpdatables.put( updatableName,updates );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    unregisterUpdatable(String updatableName)
    {
        itsUpdatables.remove( updatableName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasUpdatable(String updatableName)
    {
        return itsUpdatables.containsKey( updatableName );
    }

}

// ##########################################################################
