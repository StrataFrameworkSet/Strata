// ##########################################################################
// # File Name:	MessagingException.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataIntegrator Framework.
// #
// #   			The StrataIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.integration.messaging;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class MessagingException
    extends Exception
{

    private static final long serialVersionUID = -2078723834243463158L;

    /************************************************************************
     * Creates a new {@code MessagingException}. 
     *
     */
    protected 
    MessagingException() {}

    /************************************************************************
     * Creates a new {@code MessagingException}. 
     *
     * @param message
     */
    protected 
    MessagingException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code MessagingException}. 
     *
     * @param cause
     */
    protected 
    MessagingException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code MessagingException}. 
     *
     * @param message
     * @param cause
     */
    protected 
    MessagingException(String message,Throwable cause)
    {
        super( message,cause );
    }
}

// ##########################################################################
