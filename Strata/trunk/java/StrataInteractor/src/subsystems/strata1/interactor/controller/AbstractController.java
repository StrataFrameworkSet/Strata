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

import strata1.interactor.command.ICommand;
import strata1.interactor.event.IChangeEvent;
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
	implements IController
{
	private Map<String,ICommand>                   itsCommands;
	private Map<IChangeEvent,IHandler<IChangeEvent>> itsHandlers;
	
	public
	AbstractController()
	{
		super();
		itsCommands = new HashMap<String,ICommand>();
		itsHandlers = new HashMap<IChangeEvent,IHandler<IChangeEvent>>();
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public ICommand 
	getCommand(String commandName)
	{
		return itsCommands.get( commandName );
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
	 *  
	 *
	 * @param action
	 * @param handler
	 */
	protected void
	setCommand(String commandName,ICommand command)
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
	setHandler(IChangeEvent event,IHandler<IChangeEvent> handler)
	{
		itsHandlers.put( event,handler );
	}
}


// ##########################################################################
