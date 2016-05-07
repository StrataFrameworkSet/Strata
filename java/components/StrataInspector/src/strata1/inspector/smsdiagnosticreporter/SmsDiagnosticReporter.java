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

package strata1.inspector.smsdiagnosticreporter;

import strata1.inspector.diagnostic.AbstractMessageBasedDiagnosticReporter;
import strata1.inspector.diagnostic.IDiagnosticReporter;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SmsDiagnosticReporter 
	extends AbstractMessageBasedDiagnosticReporter
{
	private MailSender itsMailSender;
	private String     itsFromAddress;
	private String	   itsToAddress;
	private String	   itsCcAddress;
	private String	   itsBccAddress;
	
	/************************************************************************
	 * Creates a new EmailDiagnosticReporter. 
	 *
	 */
	public 
	SmsDiagnosticReporter()
	{
		super();
		itsMailSender  = null;
		itsFromAddress = "";
		itsToAddress   = "";
		itsCcAddress   = "";
		itsBccAddress  = "";		
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IDiagnosticReporter#endReport()
	 */
	@Override
	public void 
	endReport()
	{
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom( itsFromAddress );
		message.setTo( itsToAddress );
		message.setCc( itsCcAddress );
		message.setBcc( itsBccAddress );
		message.setText( getMessage() );
		
		itsMailSender.send( message );
	}

	/************************************************************************
	 *  
	 *
	 * @param sender
	 */
	public void
	setMailSender(MailSender sender)
	{
		itsMailSender = sender;
	}
}


// ##########################################################################
