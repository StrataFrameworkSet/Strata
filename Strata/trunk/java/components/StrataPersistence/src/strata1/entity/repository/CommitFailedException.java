// ##########################################################################
// # File Name:	CommitFailedException.java
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

package strata1.entity.repository;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommitFailedException
    extends RepositoryException
{

    private static final long serialVersionUID = -2460852965197781632L;

    /************************************************************************
     * Creates a new {@code CommitFailedException}. 
     *
     */
    public 
    CommitFailedException()
    {
    }

    /************************************************************************
     * Creates a new {@code CommitFailedException}. 
     *
     * @param message
     */
    public 
    CommitFailedException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code CommitFailedException}. 
     *
     * @param cause
     */
    public 
    CommitFailedException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code CommitFailedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    CommitFailedException(String message,Throwable cause)
    {
        super( message,cause );
    }

}

// ##########################################################################
