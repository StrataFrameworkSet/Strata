// ##########################################################################
// # File Name:	PrimaryMemoryCheck.java
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

package strata.diagnostic.memory;

import strata.diagnostic.core.DiagnosticCheck;
import strata.diagnostic.core.DiagnosticException;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TotalMemoryCheck 
	extends DiagnosticCheck
{
	private MemoryRange itsTotalRange;
	private boolean     itsIndicator;
	
	
	/************************************************************************
	 * Creates a new PrimaryMemoryCheck. 
	 *
	 * @param name
	 */
	public 
	TotalMemoryCheck(String name)
	{
		super( name );
		itsTotalRange = new MemoryRange( 0,Long.MAX_VALUE );		
		itsIndicator  = false;
	}

	/************************************************************************
	 *  
	 *
	 * @param range
	 */
	public void
	setTotalRange(MemoryRange range)
	{
		itsTotalRange = range;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public MemoryRange
	getTotalRange()
	{
		return itsTotalRange;
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
		StringBuilder success = new StringBuilder();
		StringBuilder failure = new StringBuilder();
		
		itsIndicator = checkTotalMemory( success,failure );
	
		if ( itsIndicator == false )
			throw new DiagnosticException( failure.toString() );
		
		return success.toString();
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
	
	/************************************************************************
	 *  
	 *
	 * @param success
	 * @param failure
	 * @return
	 */
	protected boolean 
	checkTotalMemory(StringBuilder success,StringBuilder failure)
	{
		Runtime runtime = Runtime.getRuntime();
		
		if ( itsTotalRange.inRange( runtime.totalMemory() ) )
		{
			success
				.append( "Total memory (" )
				.append( runtime.totalMemory() )
				.append( " bytes) is in specified range ("  )
				.append( itsTotalRange.getMinimumBtyes() )
				.append( ".." )
				.append( itsTotalRange.getMaximumBtyes() )
				.append( " bytes)" );
			return true;
		}
		else
		{
			failure
				.append( "Total memory (" )
				.append( runtime.totalMemory() )
				.append( " bytes) is not in specified range ("  )
				.append( itsTotalRange.getMinimumBtyes() )
				.append( ".." )
				.append( itsTotalRange.getMaximumBtyes() )
				.append( " bytes)" );
			return false;
		}
	}
}


// ##########################################################################
