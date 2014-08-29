// ##########################################################################
// # File Name:	InMemoryMessageSender.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.integrator.inmemorymessaging;

import strata1.integrator.messaging.IMessage;
import strata1.integrator.messaging.IMessageSender;
import strata1.integrator.messaging.IMessagingSession;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryMessageSender
    implements IMessageSender
{
    private final IMessagingSession    itsSession;
    private final InMemoryMessageQueue itsQueue;
    private long                       itsTimeToLive;
    
    /************************************************************************
     * Creates a new {@code InMemoryMessageSender}. 
     *
     * @param session
     * @param queue
     */
    public 
    InMemoryMessageSender(
        IMessagingSession    session,
        InMemoryMessageQueue queue)
    {
        itsSession    = session;
        itsQueue      = queue;
        itsTimeToLive = 10000;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setTimeToLive(long milliseconds)
    {
        itsTimeToLive = milliseconds;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IMessagingSession 
    getSession()
    {
        return itsSession;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    getTimeToLive()
    {
        return itsTimeToLive;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    send(IMessage message)
    {
        itsQueue.put( message );
    }

}

// ##########################################################################
