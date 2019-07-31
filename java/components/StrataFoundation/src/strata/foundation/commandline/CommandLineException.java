// ##########################################################################
// # File Name:	CommandLineException.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.foundation.commandline;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandLineException
    extends Exception
{

    private static final long serialVersionUID = 2869646853125061329L;

    /************************************************************************
     * Creates a new {@code CommandLineException}. 
     *
     */
    public 
    CommandLineException()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new {@code CommandLineException}. 
     *
     * @param message
     */
    public 
    CommandLineException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code CommandLineException}. 
     *
     * @param cause
     */
    public 
    CommandLineException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code CommandLineException}. 
     *
     * @param message
     * @param cause
     */
    public 
    CommandLineException(String message,Throwable cause)
    {
        super( message,cause );
    }
}

// ##########################################################################
