// ##########################################################################
// # File Name:	AbstractGwtView.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtClient Framework.
// #
// #   			The StrataGwtClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.gwtview;

import com.google.gwt.user.client.ui.Composite;
import java.util.HashMap;
import java.util.Map;
import strata1.client.controller.IHandler;
import strata1.client.event.IChangeEvent;
import strata1.client.view.IView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractGwtView<P>
    extends Composite
    implements IView<P>
{
    
    private String                                   itsViewName;
    private P                                        itsProvider;
    private Map<IChangeEvent,IHandler<IChangeEvent>> itsHandlers;
    
    /************************************************************************
     * Creates a new {@code AbstractGwtView}. 
     *
     */
    public
    AbstractGwtView()
    {
        this( "" );
    }
    
    /************************************************************************
     * Creates a new {@code AbstractGwtView}. 
     *
     * @param viewName
     */
    public 
    AbstractGwtView(String viewName)
    {
        super();
        itsViewName = viewName;
        itsProvider = null;
        itsHandlers = new HashMap<IChangeEvent,IHandler<IChangeEvent>>();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setProvider(P provider)
    {
        itsProvider = provider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public P 
    getProvider()
    {
        return itsProvider;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processChange(IChangeEvent event)
    {
        itsHandlers.get( event ).handle( event );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getViewName()
    {
        return itsViewName;
    }

    /************************************************************************
     *  
     *
     * @param update
     * @param handler
     */
    protected void
    setHandler(IChangeEvent event,IHandler<IChangeEvent> handler)
    {
        itsHandlers.put( event,handler );
    }
}

// ##########################################################################
