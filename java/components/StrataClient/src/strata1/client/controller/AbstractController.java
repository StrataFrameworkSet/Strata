// ##########################################################################
// # File Name:	.java
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

package strata1.client.controller;

import strata1.client.event.IChangeEvent;
import strata1.client.model.IModel;
import strata1.client.view.IView;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractController<P,V extends IView<P>,M extends IModel>
	implements IController<P,V,M>
{
    private Map<
        Class<
            ? extends IChangeEvent>,
            IHandler<IChangeEvent>>  itsHandlers;
    private V                        itsView;
    private M                        itsModel;
    	
	/************************************************************************
	 * Creates a new {@code AbstractController}. 
	 *
	 */
	protected
	AbstractController()
	{
		super();
        itsHandlers = 
            new HashMap<
                Class<? extends IChangeEvent>,IHandler<IChangeEvent>>();
		itsView  = null;
		itsModel = null;
	}
	

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processChange(IChangeEvent event)
    {
    	itsHandlers.get( event.getClass() ).handle( event );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public V 
    getView()
    {
        return itsView;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public M 
    getModel()
    {
        return itsModel;
    }

    /************************************************************************
     *  
     *
     * @param update
     * @param handler
     */
    protected void
    setHandler(
        Class<? extends IChangeEvent> event,
        IHandler<IChangeEvent> handler)
    {
    	itsHandlers.put( event,handler );
    }
    
    /************************************************************************
     *  
     *
     * @param view
     */
    protected void
    setView(V view,P downcastedThis)
    {
        itsView = view;
        itsView.setProvider( downcastedThis );
    }
    
    /************************************************************************
     *  
     *
     * @param model
     */
    protected void
    setModel(M model)
    {
        itsModel = model;
        itsModel.setProcessor( this );
    }
}


// ##########################################################################
