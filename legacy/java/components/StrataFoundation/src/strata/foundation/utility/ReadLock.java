// ##########################################################################
// # File Name:	ReadLock.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundation Framework.
// #
// #   			The StrataFoundation Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundation Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundation
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.utility;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ReadLock 
    implements AutoCloseable
{
    private final ISynchronizer itsSynchronizer;
    
    /************************************************************************
     * Creates a new ReadLock. 
     *
     */
    public 
    ReadLock(ISynchronizer synchronizer)
    {
        itsSynchronizer = synchronizer;
        itsSynchronizer.lockForReading();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close()
    {
        itsSynchronizer.unlockFromReading();
    }

}

// ##########################################################################
