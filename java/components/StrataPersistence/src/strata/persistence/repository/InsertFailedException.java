// ##########################################################################
// # File Name:	InsertFailedException.java
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

package strata.persistence.repository;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InsertFailedException
    extends RepositoryException
{

    private static final long serialVersionUID = 2733379335038530686L;

    /************************************************************************
     * Creates a new {@code InsertFailedException}. 
     *
     */
    public 
    InsertFailedException()
    {
    }

    /************************************************************************
     * Creates a new {@code InsertFailedException}. 
     *
     * @param message
     */
    public 
    InsertFailedException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code InsertFailedException}. 
     *
     * @param cause
     */
    public 
    InsertFailedException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code InsertFailedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    InsertFailedException(String message,Throwable cause)
    {
        super( message,cause );
    }

}

// ##########################################################################
