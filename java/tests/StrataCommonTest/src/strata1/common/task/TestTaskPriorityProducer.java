// ##########################################################################
// # File Name:	TestTaskProducer.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommonTest Framework.
// #
// #   			The StrataCommonTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommonTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommonTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.task;

import strata1.common.producerconsumer.DispatchKind;
import java.util.LinkedList;
import java.util.Queue;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TestTaskPriorityProducer
    extends    AbstractTaskPriorityProducer
    implements ITaskPriorityProducer
{
    private Queue<ITask> itsTasks;
    private DispatchKind itsKind;
    
    /************************************************************************
     * Creates a new {@code TestTaskProducer}. 
     *
     */
    public 
    TestTaskPriorityProducer(DispatchKind kind)
    {
        itsTasks = new LinkedList<ITask>();
        itsKind  = kind;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProducing()
    {
        if ( getPriorityDispatcher() == null )
            throw new IllegalStateException("no sink available");
        
        for (ITask task: itsTasks)
        {
            try
            {
                if ( itsKind == DispatchKind.ROUTE )
                    getPriorityDispatcher().route( 1,task );
                else
                    getPriorityDispatcher().broadcast( 1,task );
            }
            catch(Exception e)
            {
                throw new IllegalStateException(e);
            }
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stopProducing()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isProducing()
    {
        return !itsTasks.isEmpty();
    }

    /************************************************************************
     *  
     *
     * @param task
     * @return
     */
    public TestTaskPriorityProducer
    insertTask(ITask task)
    {
        itsTasks.add( task );
        return this;
    }
    
}

// ##########################################################################
