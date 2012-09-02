// ##########################################################################
// # File Name:	CommandException.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataGwtInteractor Framework.
// #
// #   			The StrataGwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataGwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataGwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.gwtclient.commandclient;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandException
    extends Exception
{
    private static final long serialVersionUID = -7779338879892803461L;

    /************************************************************************
     * Creates a new {@code CommandException}. 
     *
     */
    public 
    CommandException()
    {
    }

    /************************************************************************
     * Creates a new {@code CommandException}. 
     *
     * @param message
     */
    public 
    CommandException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code CommandException}. 
     *
     * @param cause
     */
    public 
    CommandException(Throwable cause)
    {
        super( cause.getMessage() );
        // TODO Auto-generated constructor stub
    }

}

// ##########################################################################
