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

package strata1.interactor.controller;

import strata1.interactor.command.Command;
import strata1.interactor.command.CommandProvider;
import strata1.interactor.event.ChangeEvent;
import strata1.interactor.event.ChangeEventProcessor;
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
class AbstractController
	implements Controller
{
	private Map<String,Command>                   itsCommands;
	private Map<ChangeEvent,Handler<ChangeEvent>> itsHandlers;
	
	public
	AbstractController()
	{
		super();
		itsCommands = new HashMap<String,Command>();
		itsHandlers = new HashMap<ChangeEvent,Handler<ChangeEvent>>();
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public Command 
	getCommand(String commandName)
	{
		return itsCommands.get( commandName );
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	processChange(ChangeEvent event)
	{
		itsHandlers.get( event ).handle( event );
	}

	/************************************************************************
	 *  
	 *
	 * @param action
	 * @param handler
	 */
	protected void
	setCommand(String commandName,Command command)
	{
		itsCommands.put( commandName,command );
	}
	
	/************************************************************************
	 *  
	 *
	 * @param update
	 * @param handler
	 */
	protected void
	setHandler(ChangeEvent event,Handler<ChangeEvent> handler)
	{
		itsHandlers.put( event,handler );
	}
}


// ##########################################################################
