// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.persistence.repository;

/**
 * Invalid inputs to methods in the Example package.
 * 
 * @author 		
 *     Java Persistence Strategy Workgroup 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class InvalidInputException 
	extends AbstractRepositoryException
{
	private static final long serialVersionUID = 0;

	/************************************************************************
	 * Creates a new InvalidFormatException. 
	 *
	 */
	public InvalidInputException()
	{
		super();
	}

	/************************************************************************
	 * Creates a new InvalidFormatException. 
	 *
	 * @param arg0
	 */
	public InvalidInputException(String arg0)
	{
		super( arg0 );
	}

	/************************************************************************
	 * Creates a new InvalidFormatException. 
	 *
	 * @param arg0
	 * @param arg1
	 */
	public InvalidInputException(String arg0,Throwable arg1)
	{
		super( arg0,arg1 );
	}

	/************************************************************************
	 * Creates a new InvalidFormatException. 
	 *
	 * @param arg0
	 */
	public InvalidInputException(Throwable arg0)
	{
		super( arg0 );
	}

}


// ##########################################################################
