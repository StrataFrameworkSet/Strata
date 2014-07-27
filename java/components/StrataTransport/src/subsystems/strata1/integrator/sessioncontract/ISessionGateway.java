// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
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

package strata1.integrator.sessioncontract;

import strata1.integrator.annotation.Asynchronous;
import strata1.integrator.annotation.Callback;
import strata1.integrator.annotation.Gateway;

/**
 * <a href="http://martinfowler.com/eaaCatalog/gateway.html">Gateway</a> 
 * for starting and finishing application sessions.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@Gateway
public 
interface ISessionGateway
{
	/************************************************************************
	 * Opens a session for an anonymous (unauthenticated) user.
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ISessionGateway</code> method calls.
	 * 
	 * @see				ISessionOutputReceiver#onOpenSession(
	 * 						SessionSummary)
	 */
	@Asynchronous
	public void
	openSession(
		@Callback(method="onOpenSession",exception="onOpenSession")
		ISessionOutputReceiver output);
	
	/************************************************************************
	 * Opens a session for an authenticated user.
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ISessionGateway</code> method calls.
	 * @param input		authentication data (e.g. username and password, 
	 * 					digital certificate, ...)
	 * 
	 * @see				ISessionOutputReceiver#onOpenSession(
	 * 						SessionSummary)
	 */
	@Asynchronous
	public void
	openSession(
		@Callback(method="onOpenSession",exception="onOpenSession")
		ISessionOutputReceiver output,
		AuthenticationPacket  input);
	
	/************************************************************************
	 * Closes a session.
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ISessionGateway</code> method calls.
	 * @param input		identifies session to finish
	 * 
	 * @see				ISessionOutputReceiver#onCloseSession(SessionId)
	 */
	@Asynchronous
	public void
	closeSession(
		@Callback(method="onCloseSession",exception="onCloseSession")
		ISessionOutputReceiver output,
		SessionId 			  input);
}

// ##########################################################################
