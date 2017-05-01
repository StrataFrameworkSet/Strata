// ##########################################################################
// # File Name:	AbstractDispatcher.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.presentation.shell;

import java.util.concurrent.ConcurrentLinkedQueue;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractDispatcher
    implements IDispatcher
{
    private ConcurrentLinkedQueue<Runnable> itsTasks;
    
    /************************************************************************
     * Creates a new {@code AbstractDispatcher}. 
     *
     */
    public 
    AbstractDispatcher()
    {
        itsTasks = new ConcurrentLinkedQueue<Runnable>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    insertTask(Runnable task)
    {
        itsTasks.add( task );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    removeTask(Runnable task)
    {
        itsTasks.remove( task );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Runnable 
    getNextTask()
    {
        return itsTasks.poll();
    }

}

// ##########################################################################
