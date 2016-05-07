// ##########################################################################
// # File Name:	UpdateException.java
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

package strata1.gwtclient.updateclient;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class UpdateException
    extends Exception
{

    private static final long serialVersionUID = 4471404439362661312L;

    /************************************************************************
     * Creates a new {@code UpdateException}. 
     *
     */
    public 
    UpdateException()
    {
    }

    /************************************************************************
     * Creates a new {@code UpdateException}. 
     *
     * @param message
     */
    public 
    UpdateException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code UpdateException}. 
     *
     * @param cause
     */
    public UpdateException(Throwable cause)
    {
        this( cause.getMessage() );
    }

}

// ##########################################################################
