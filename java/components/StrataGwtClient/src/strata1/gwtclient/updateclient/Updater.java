// ##########################################################################
// # File Name:	Updater.java
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

package strata1.gwtclient.updateclient;

import com.google.gwt.core.client.GWT;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Updater
    implements IUpdater
{
    private boolean itsActiveIndicator;
    
    /************************************************************************
     * Creates a new {@code Updater}. 
     *
     */
    public 
    Updater() 
    {
        activate();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    activate()
    {
        synchronized (this)
        {
            itsActiveIndicator = true;
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    deactivate()
    {
        synchronized (this)
        {
            itsActiveIndicator = false;
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive()
    {
        synchronized (this)
        {
            return itsActiveIndicator;
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    updateView(IUpdatable view)
    {
        UpdateRequest          request = null;
        UpdateResponseReceiver response = null;
        IUpdateServiceAsync    service  = null;
        
        if ( isActive() )
        {
            request  = new UpdateRequest(view.getUpdatableName());
            response = new UpdateResponseReceiver(view,this);
            service  = GWT.create( IUpdateService.class );
            
            service.getUpdate( request,response );
        }
    }

}

// ##########################################################################
