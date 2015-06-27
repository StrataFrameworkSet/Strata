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
 * Base class for all RepositoryImp exceptions.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@SuppressWarnings("serial")
public abstract 
class AbstractRepositoryException 
	extends Exception
{
	/************************************************************************
	 * Creates a new AbstractShareholderException. 
	 *
	 */
	public 
	AbstractRepositoryException()
	{
		super();
	}

	/************************************************************************
	 * Creates a new AbstractShareholderException. 
	 *
	 * @param arg0
	 */
	public 
	AbstractRepositoryException(String arg0)
	{
		super( arg0 );
	}

	/************************************************************************
	 * Creates a new AbstractShareholderException. 
	 *
	 * @param arg0
	 * @param arg1
	 */
	public 
	AbstractRepositoryException(String arg0,Throwable arg1)
	{
		super( arg0,arg1 );
	}

	/************************************************************************
	 * Creates a new AbstractShareholderException. 
	 *
	 * @param arg0
	 */
	public 
	AbstractRepositoryException(Throwable arg0)
	{
		super( arg0 );
	}

}


// ##########################################################################
