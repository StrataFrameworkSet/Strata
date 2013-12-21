// ##########################################################################
// # File Name:	BlockingCollectionClosedException.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.common.producerconsumer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BlockingCollectionClosedException
    extends Exception
{

    private static final long serialVersionUID = 4257091064178302337L;

    /************************************************************************
     * Creates a new {@code BlockingCollectionClosedException}. 
     *
     */
    public 
    BlockingCollectionClosedException()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * Creates a new {@code BlockingCollectionClosedException}. 
     *
     * @param message
     */
    public 
    BlockingCollectionClosedException(String message)
    {
        super( message );
    }

    /************************************************************************
     * Creates a new {@code BlockingCollectionClosedException}. 
     *
     * @param cause
     */
    public 
    BlockingCollectionClosedException(Throwable cause)
    {
        super( cause );
    }

    /************************************************************************
     * Creates a new {@code BlockingCollectionClosedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    BlockingCollectionClosedException(String message,Throwable cause)
    {
        super( message,cause );
     }

    /************************************************************************
     * Creates a new {@code BlockingCollectionClosedException}. 
     *
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BlockingCollectionClosedException(
        String    message,
        Throwable cause,
        boolean   enableSuppression,
        boolean   writableStackTrace)
    {
        super( message,cause,enableSuppression,writableStackTrace );
    }

}

// ##########################################################################
