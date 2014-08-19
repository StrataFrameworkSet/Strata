// ##########################################################################
// # File Name:	NoMessageReceivedException.java
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

package strata1.integrator.messaging;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class NoMessageReceivedException
    extends MessagingException
{

    private static final long serialVersionUID = -7932973696922873897L;

    /************************************************************************
     * Creates a new {@code NoMessageReceivedException}. 
     *
     */
    public 
    NoMessageReceivedException() {}

    /************************************************************************
     * Creates a new {@code NoMessageReceivedException}. 
     *
     * @param message
     */
    public 
    NoMessageReceivedException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code NoMessageReceivedException}. 
     *
     * @param cause
     */
    public 
    NoMessageReceivedException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code NoMessageReceivedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    NoMessageReceivedException(String message,Throwable cause)
    {
        super( message,cause );
    }

}

// ##########################################################################
