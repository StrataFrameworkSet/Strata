// ##########################################################################
// # File Name:	TaskRouter.java
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

package strata1.common.task;

import strata1.common.utility.ISynchronizer;
import strata1.common.utility.ReadWriteLockSynchronizer;
import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TaskRouter
    implements ITaskRouter
{
    private Set<ITaskConsumer> itsConsumers;
    private ISynchronizer      itsSynchronizer;
    
    /************************************************************************
     * Creates a new {@code TaskRouter}. 
     *
     */
    public 
    TaskRouter()
    {
        itsConsumers = new HashSet<ITaskConsumer>();
        itsSynchronizer = new ReadWriteLockSynchronizer();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    attachConsumer(ITaskConsumer consumer)
    {
        try
        {
            itsSynchronizer.lockForWriting();
            
            if ( !hasConsumer( consumer ) )
                itsConsumers.add( consumer );
                
        }
        finally
        {
            itsSynchronizer.unlockFromWriting();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    detachConsumer(ITaskConsumer consumer)
    {
        // TODO Auto-generated method stub

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    routeElement(ITask element)
    {
        // TODO Auto-generated method stub

    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasConsumer(ITaskConsumer consumer)
    {
        // TODO Auto-generated method stub
        return false;
    }

}

// ##########################################################################
