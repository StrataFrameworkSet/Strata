// ##########################################################################
// # File Name:	ExecutionException.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.command;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ExecutionException
    extends Exception
{
    private static final long serialVersionUID = -5131170444977506564L;

    /************************************************************************
     * Creates a new ExecutionException. 
     *
     */
    public 
    ExecutionException()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new ExecutionException. 
     *
     * @param message
     */
    public 
    ExecutionException(String message)
    {
        super(
            message );
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new ExecutionException. 
     *
     * @param cause
     */
    public 
    ExecutionException(Throwable cause)
    {
        super(
            cause );
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new ExecutionException. 
     *
     * @param message
     * @param cause
     */
    public 
    ExecutionException(String message,Throwable cause)
    {
        super( message,cause );
    }

}


// ##########################################################################
