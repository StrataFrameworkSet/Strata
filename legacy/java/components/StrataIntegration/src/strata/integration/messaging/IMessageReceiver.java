// ##########################################################################
// # File Name:	IMessageReceiver.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.integration.messaging;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IMessageReceiver
{
    public IMessageReceiver
    setListener(IMessageListener listener)
        throws MixedModeException;
    
    public IMessagingSession
    getSession();

    public IMessageListener
    getListener();
    
    public String
    getSelector();
    
    public void
    startListening()
        throws MixedModeException;
    
    public void
    stopListening()
        throws MixedModeException;
    
    public boolean
    isListening();
    
    public IMessage
    receive()
        throws MixedModeException;
    
    public IMessage
    receive(long timeOutInMs)
        throws MixedModeException,NoMessageReceivedException;
    
    public IMessage
    receiveNoWait()
        throws MixedModeException,NoMessageReceivedException;
    
    public void
    close();
}

// ##########################################################################
