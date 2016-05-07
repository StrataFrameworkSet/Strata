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

import java.security.cert.Certificate;

/**
 * <a href=
 * "http://java.sun.com/
 * blueprints/corej2eepatterns/Patterns/TransferObject.html">
 * Transport Object</a> for digital certificates.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CertificatePacket 
	extends AuthenticationPacket
{
	private Certificate itsCertificate;
	
	/************************************************************************
	 * Creates a new CertificatePacket. 
	 *
	 * @param 			certificate	a digital certificate identifying 
	 * 								a principal
	 * @postcondition	this.getCertificate() == certificate
	 */
	public 
	CertificatePacket(Certificate certificate)
	{
		super();
		itsCertificate = certificate;
	}

	/************************************************************************
	 * Creates a new CertificatePacket. 
	 *
	 * @param 			session		an existing (usually anonymous) session
	 * @param 			certificate	a digital certificate identifying 
	 * 								a principal
	 * @postcondition	this.getCertificate() == certificate
	 */
	public 
	CertificatePacket(
		SessionRep session,
		Certificate 	 certificate)
	{
		super( session );
		itsCertificate = certificate;
	}

	/************************************************************************
	 * Returns this <code>CertificatePacket</code>'s certificate. 
	 *
	 * @return	a digital certificate identifying a principal
	 */
	public Certificate
	getCertificate()
	{
		return itsCertificate;
	}
}


// ##########################################################################
