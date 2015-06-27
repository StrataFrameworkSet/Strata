// ##########################################################################
// # File Name:	RepositoryException.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@SuppressWarnings("serial")
public abstract 
class RepositoryException
    extends Exception
{

    /************************************************************************
     * Creates a new {@code RepositoryException}. 
     *
     */
    public 
    RepositoryException()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new {@code RepositoryException}. 
     *
     * @param message
     */
    public 
    RepositoryException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code RepositoryException}. 
     *
     * @param cause
     */
    public 
    RepositoryException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code RepositoryException}. 
     *
     * @param message
     * @param cause
     */
    public 
    RepositoryException(String message,Throwable cause)
    {
        super( message,cause );
    }
}

// ##########################################################################
