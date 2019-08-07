// ##########################################################################
// # File Name:	LessThanFileSizeEvaluator.java
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

package strata.diagnostic.filesystem;

import java.io.File;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class EqualToFileSizeEvaluator 
	implements FileSizeEvaluator
{

	/************************************************************************
	 * Creates a new LessThanFileSizeEvaluator. 
	 *
	 */
	public 
	EqualToFileSizeEvaluator()
	{
		super();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see FileSizeEvaluator#evaluate(File,long)
	 */
	@Override
	public boolean 
	evaluate(File file,long size)
	{
		return file.length() == size;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see FileSizeEvaluator#getMessage(File,long)
	 */
	@Override
	public String 
	getMessage(File file,long size)
	{
		StringBuilder message = new StringBuilder();
		
		message
			.append( "File " )
			.append( file.getAbsolutePath() )
			.append( "size (" )
			.append( file.length() )
			.append( ") " );
		
		if ( evaluate( file,size ) )
			message.append( " is equal to " );
		else
			message.append( " is not equal to " );
			
		message.append( size ).append( "." );
		return message.toString();
	}

}


// ##########################################################################
