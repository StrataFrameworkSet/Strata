// ##########################################################################
// # File Name:	ModelViewControllerApp.java
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

package strata1.client.dispatcher;

import strata1.client.controller.IController;
import strata1.client.model.IModel;
import strata1.client.view.IView;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class ModelViewControllerApp<
    M extends IModel,
    V extends IView,
    C extends IController>
    implements Runnable
{
    private M itsModel;
    private V itsView;
    private C itsController;
    
    /************************************************************************
     * Creates a new ModelViewControllerApp. 
     */
    public 
    ModelViewControllerApp()
    {
        itsModel      = null;
        itsView       = null;
        itsController = null;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run()
    {
        itsController.start();
    }
    
    /************************************************************************
     *  
     *
     * @param model
     * @param view
     * @param controller
     */
    protected void
    configure(M model,V view,C controller)
    {
        itsModel      = model;
        itsView       = view;
        itsController = controller;
        
        itsModel.setProcessor( itsController );
        itsView.setProvider( itsController );
    }
}


// ##########################################################################
