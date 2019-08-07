// ##########################################################################
// # File Name:	FreeMemoryCheck.java
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
class FreeMemoryCheck 
	extends DiagnosticCheck
{
	private MemoryRange itsFreeRange;
	private boolean     itsIndicator;
	
	
	/************************************************************************
	 * Creates a new PrimaryMemoryCheck. 
	 *
	 * @param name
	 */
	public 
	FreeMemoryCheck(String name)
	{
		super( name );
		itsFreeRange = new MemoryRange( 0,Long.MAX_VALUE );		
		itsIndicator  = false;
	}

	/************************************************************************
	 *  
	 *
	 * @param range
	 */
	public void
	setFreeRange(MemoryRange range)
	{
		itsFreeRange = range;
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public MemoryRange
	getFreeRange()
	{
		return itsFreeRange;
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
		
		itsIndicator = checkFreeMemory( success,failure );
	
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
		Runtime       runtime = Runtime.getRuntime();
		StringBuilder success = new StringBuilder();
		StringBuilder failure = new StringBuilder();
		
		runtime.gc();
		itsIndicator = checkFreeMemory( success,failure );
	
		if ( itsIndicator == false )
			throw new DiagnosticException( failure.toString() );
		
		return success.toString();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see DiagnosticCheck#isRecoverable()
	 */
	@Override
	protected boolean 
	isRecoverable()
	{
		return true;
	}
	
	/************************************************************************
	 *  
	 *
	 * @param success
	 * @param failure
	 * @return
	 */
	protected boolean 
	checkFreeMemory(StringBuilder success,StringBuilder failure)
	{
		Runtime runtime = Runtime.getRuntime();
		
		if ( itsFreeRange.inRange( runtime.freeMemory() ) )
		{
			success
				.append( "Free memory (" )
				.append( runtime.freeMemory() )
				.append( " bytes) is in specified range ("  )
				.append( itsFreeRange.getMinimumBtyes() )
				.append( ".." )
				.append( itsFreeRange.getMaximumBtyes() )
				.append( " bytes)" );
			return true;
		}
		else
		{
			failure
				.append( "Free memory (" )
				.append( runtime.freeMemory() )
				.append( " bytes) is not in specified range ("  )
				.append( itsFreeRange.getMinimumBtyes() )
				.append( ".." )
				.append( itsFreeRange.getMaximumBtyes() )
				.append( " bytes)" );
			return false;
		}
	}
}


// ##########################################################################
