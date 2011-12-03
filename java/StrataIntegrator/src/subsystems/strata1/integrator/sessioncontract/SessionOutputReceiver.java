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

/**
 * Receives output from <code>SessionGateway</code> method calls.
 * 
 * @see SessionGateway
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface SessionOutputReceiver
{
	/************************************************************************
	 * Processes the output from <code>SessionGateway.openSession</code>. 
	 * 
	 * @see		SessionGateway#openSession(SessionOutputReceiver)
	 * @see 	SessionGateway#openSession(
	 * 				SessionOutputReceiver,
	 * 				AuthenticationPacket)
	 * @see 	SessionGateway#openSession(
	 * 				SessionOutputReceiver,
	 * 				CertificatePacket)
	 * 
	 * @param 	session	a newly started session
	 */
	public void
	onOpenSession(SessionRep session);

	/************************************************************************
	 * Processes an exception from <code>SessionGateway.openSession</code>. 
	 * 
	 * @see		SessionGateway#openSession(SessionOutputReceiver)
	 * @see 	SessionGateway#openSession(
	 * 				SessionOutputReceiver,
	 * 				AuthenticationPacket)
	 * @see 	SessionGateway#openSession(
	 * 				SessionOutputReceiver,
	 * 				CertificatePacket)
	 * 
	 * @param 	session	a newly started session
	 */
	public void
	onOpenSession(Throwable exception);

	/************************************************************************
	 * Processes the output from <code>SessionGateway.closeSession</code>.
	 * 
	 * @see		SessionGateway#closeSession(
	 * 				SessionOutputReceiver,
	 * 				SessionId)
	 *
	 * @param 	sessionId	identifier of finished session
	 */
	public void
	onCloseSession(SessionId sessionId);
	
	/************************************************************************
	 * Process an exception from <code>SessionGateway.closeSession</code>.
	 *
	 * @see		SessionGateway#closeSession(
	 * 				SessionOutputReceiver,
	 * 				SessionId)
	 * 
	 * @param 	exception	an exception from <code>SessionGatway</code>
	 * 						method calls.
	 */
	public void
	onCloseSession(Throwable exception);
}


// ##########################################################################
