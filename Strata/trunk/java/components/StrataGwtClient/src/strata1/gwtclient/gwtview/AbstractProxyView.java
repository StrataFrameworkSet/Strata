// ##########################################################################
// # File Name:	AbstractProxyView.java
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

package strata1.gwtclient.gwtview;

import strata1.client.command.ICommandInvokerManager;
import strata1.client.view.AbstractView;
import strata1.gwtclient.updateclient.UpdateResponse;
import strata1.gwtclient.updateserver.IUpdatableManager;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractProxyView
    extends AbstractView
{
    private BlockingQueue<UpdateResponse> itsUpdates;
    
    /************************************************************************
     * Creates a new {@code AbstractProxyView}. 
     *
     * @param viewName
     */
    public 
    AbstractProxyView(
        String viewName,
        ICommandInvokerManager invokerManager,
        IUpdatableManager      updatableManager)
    {
        itsUpdates  = new LinkedBlockingQueue<UpdateResponse>();
        invokerManager.registerInvoker( this );
        updatableManager.registerUpdatable( getViewName(),itsUpdates );
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        UpdateResponse response = new UpdateResponse( getViewName() );
        
        response.setUpdateAction( "start" );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        UpdateResponse response = new UpdateResponse( getViewName() );
        
        response.setUpdateAction( "stop" );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        UpdateResponse response = new UpdateResponse( getViewName() );
        
        response.setUpdateAction( "show" );
        sendUpdate( response );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        UpdateResponse response = new UpdateResponse( getViewName() );
        
        response.setUpdateAction( "hide" );
        sendUpdate( response );
    }

    /************************************************************************
     *  
     *
     * @param response
     */
    protected void
    sendUpdate(UpdateResponse response)
    {
        try
        {
            itsUpdates.put( response );
        }
        catch(InterruptedException e)
        {
        }
    }
}

// ##########################################################################
