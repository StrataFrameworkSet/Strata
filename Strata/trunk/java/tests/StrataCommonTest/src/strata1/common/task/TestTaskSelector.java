// ##########################################################################
// # File Name:	TestTaskSelector.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TestTaskSelector
    implements ITaskSelector
{
    private int itsTaskId;
    
    /************************************************************************
     * Creates a new {@code TestTaskSelector}. 
     *
     */
    public 
    TestTaskSelector(int taskId)
    {
        itsTaskId = taskId;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    match(ITask task)
    {
        if ( task.hasProperty( Integer.class,"taskId" ) )
            return 
                task
                    .getProperty( Integer.class,"taskId" )
                    .equals(itsTaskId);
            
        return false;
    }

}

// ##########################################################################
