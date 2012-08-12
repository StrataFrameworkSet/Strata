// ##########################################################################
// # File Name:	UpdateResponseReceiver.java
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

package strata1.gwtinteractor.updateclient;

import com.google.gwt.user.client.rpc.AsyncCallback;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UpdateResponseReceiver
    implements AsyncCallback<UpdateResponse>
{
    private IUpdatable itsView;
    private IUpdater   itsUpdater;
    
    /************************************************************************
     * Creates a new {@code UpdateResponseReceiver}. 
     *
     */
    public 
    UpdateResponseReceiver(IUpdatable view,IUpdater updater)
    {
        itsView    = view;
        itsUpdater = updater;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onSuccess(UpdateResponse response)
    {
        try
        {
            itsView.update( response );
        }
        finally
        {
            itsUpdater.updateView( itsView );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onFailure(Throwable caught)
    {
        try
        {
            
        }
        finally
        {
            itsUpdater.updateView( itsView );
        }
    }

}

// ##########################################################################
