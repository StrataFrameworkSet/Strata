// ##########################################################################
// # File Name:	EmailDiagnosticReporter.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInspector Framework.
// #
// #   			The StrataInspector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInspector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInspector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.diagnostic.core;

import strata.foundation.mail.IMailMessage;
import strata.foundation.mail.IMailSender;
import strata.foundation.mail.IMailSession;
import strata.foundation.value.EmailAddress;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class EmailDiagnosticReporter 
	extends AbstractMessageBasedDiagnosticReporter
{
	private IMailSession itsSession;
	private EmailAddress itsFromAddress;
	private EmailAddress itsToAddress;
	private EmailAddress itsCcAddress;
	private EmailAddress itsBccAddress;
	
	/************************************************************************
	 * Creates a new EmailDiagnosticReporter. 
	 *
	 */
	public 
	EmailDiagnosticReporter()
	{
		super();
		itsSession     = null;
		itsFromAddress = null;
		itsToAddress   = null;
		itsCcAddress   = null;
		itsBccAddress  = null;		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#endReport()
	 */
	@Override
	public void 
	endReport()
	{
		IMailMessage message = itsSession.createMessage();
		IMailSender  sender  = itsSession.createSender();
		
		message.setFrom( itsFromAddress );
		message.setTo( itsToAddress );
		message.setCc( itsCcAddress );
		message.setBcc( itsBccAddress );
		message.setText( getMessage() );
		
		sender.send( message );
	}

	/************************************************************************
	 *  
	 *
	 * @param sender
	 */
	public void
	setSession(IMailSession session)
	{
		itsSession = session;
	}
}


// ##########################################################################
