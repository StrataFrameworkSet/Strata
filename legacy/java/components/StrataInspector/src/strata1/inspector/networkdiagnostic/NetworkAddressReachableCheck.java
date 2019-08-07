// ##########################################################################
// # File Name:	NetworkAddressReachableCheck.java
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

package strata1.inspector.networkdiagnostic;

import strata1.inspector.diagnostic.DiagnosticCheck;
import strata1.inspector.diagnostic.DiagnosticException;
import java.io.IOException;
import java.net.InetAddress;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class NetworkAddressReachableCheck 
	extends DiagnosticCheck
{
	private InetAddress itsAddress;
	private int         itsTimeout;

	/************************************************************************
	 * Creates a new ProcessExistsCheck. 
	 *
	 * @param name
	 */
	public 
	NetworkAddressReachableCheck(String name)
	{
		super( name );
		itsAddress = null;
	}

	/************************************************************************
	 *  
	 *
	 * @param address
	 */
	public void 
	setNetworkAddress(InetAddress address)
	{
		itsAddress = address;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param timeout
	 */
	public void 
	setTimeout(int timeout)
	{
		itsTimeout = timeout;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public InetAddress
	getNetworkAddress()
	{
		return itsAddress;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public int
	getTimeout()
	{
		return itsTimeout;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#runCheck()
	 */
	@Override
	protected String 
	runCheck() 
		throws DiagnosticException
	{
		try
		{
			
			if ( itsAddress.isReachable( itsTimeout ) )
				return "Network address " + itsAddress + " is reachable.";
			else
				throw 
					new DiagnosticException( 
						"Network address " + itsAddress + " is not reachable." );
			
		}
		catch (IOException e)
		{
			throw new DiagnosticException( e );
		}
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#runRecovery()
	 */
	@Override
	protected String 
	runRecovery() 
		throws DiagnosticException
	{
		throw 
			new DiagnosticException(
				"This diagnostic check is not recoverable." );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#isRecoverable()
	 */
	@Override
	protected boolean 
	isRecoverable()
	{
		return false;
	}

}


// ##########################################################################
