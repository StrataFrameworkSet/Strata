// ##########################################################################
// # File Name:	CommandService.java
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

package strata1.gwtclient.commandserver;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.HashMap;
import java.util.Map;
import strata.presentation.command.ICommandInvoker;
import strata.presentation.command.ICommandInvokerManager;
import strata1.gwtclient.commandclient.CommandException;
import strata1.gwtclient.commandclient.CommandRequest;
import strata1.gwtclient.commandclient.ICommandService;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandService
    extends    RemoteServiceServlet
    implements ICommandService,
               ICommandInvokerManager
{

    private static final long serialVersionUID = 2419004536014123182L;
    
    private Map<String,ICommandInvoker> itsInvokers;

    /************************************************************************
     * Creates a new {@code CommandService}. 
     *
     */
    public 
    CommandService()
    {
        itsInvokers = new HashMap<String,ICommandInvoker>();
    }

    /************************************************************************
     * Creates a new {@code CommandService}. 
     *
     * @param delegate
     */
    public 
    CommandService(Object delegate)
    {
        super( delegate );
        itsInvokers = new HashMap<String,ICommandInvoker>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    invoke(CommandRequest request) 
        throws CommandException
    {
        if ( !hasInvoker( request.getInvokerName() ) )
            throw 
                new CommandException( 
                    "No such invoker: " + request.getInvokerName() + "." );
        try
        {
            getInvoker( request.getInvokerName() )
                .invoke( request.getCommandName() );
        }
        catch (Throwable caught)
        {
            throw new CommandException( caught );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerInvoker(ICommandInvoker invoker)
    {
        itsInvokers.put( invoker.getInvokerName(),invoker );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    unregisterInvoker(ICommandInvoker invoker)
    {
        itsInvokers.remove( invoker.getInvokerName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandInvoker 
    getInvoker(String invokerName)
    {
        return itsInvokers.get( invokerName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasInvoker(String invokerName)
    {
        return itsInvokers.containsKey( invokerName );
    }
    
}

// ##########################################################################
