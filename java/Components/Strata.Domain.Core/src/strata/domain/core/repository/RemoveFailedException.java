// ##########################################################################
// # File Name:	RemoveFailedException.java
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

package strata.domain.core.repository;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class RemoveFailedException
    extends RepositoryException
{

    private static final long serialVersionUID = -2611897351868596614L;

    /************************************************************************
     * Creates a new {@code RemoveFailedException}. 
     *
     */
    public 
    RemoveFailedException()
    {
    }

    /************************************************************************
     * Creates a new {@code RemoveFailedException}. 
     *
     * @param message
     */
    public 
    RemoveFailedException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code RemoveFailedException}. 
     *
     * @param cause
     */
    public 
    RemoveFailedException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code RemoveFailedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    RemoveFailedException(String message,Throwable cause)
    {
        super( message,cause );
    }

}

// ##########################################################################
