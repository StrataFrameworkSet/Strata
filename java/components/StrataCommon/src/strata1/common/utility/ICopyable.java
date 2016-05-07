// ##########################################################################
// # File Name:	ICopyable.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.utility;

/**
 * A more natural alternative to Cloneable and Object.clone().
 * Also takes advantage of Java 6's covariant return feature:
 * extending interfaces and implementing classes can extend
 * the return class of the {@code copy()} method to types 
 * that extend or implement ICopyable.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ICopyable
{
	/************************************************************************
	 * Returns a copy of the object. An alternative to {@code Object.clone()}. 
	 *
	 * @return copy of object
	 */
	public ICopyable
	copy();
}


// ##########################################################################
