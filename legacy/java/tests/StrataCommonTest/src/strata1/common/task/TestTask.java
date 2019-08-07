// ##########################################################################
// # File Name:	TestTask.java
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

import strata1.common.logger.ILogger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TestTask
    extends AbstractTask
{
    private ILogger itsLogger;
    
    /************************************************************************
     * Creates a new {@code TestTask}. 
     *
     * @param name
     */
    public 
    TestTask(String name,Integer taskId,ILogger logger)
    {
        super( name );
        itsLogger = logger;
        setProperty( "taskId",taskId );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    execute()
    {
        StringBuilder builder = new StringBuilder();
        
        builder
            .append( "execute()\n" )
            .append( "      name = " )
            .append( getName() ).append( "\n" )
            .append( "    taskId = ")
            .append( getProperty(Integer.class,"taskId")).append( "\n" )
            .append( "    thread = " )
            .append( Thread.currentThread().getName() ).append( "\n" );
        
        itsLogger.logInfo( builder.toString() );
    }

}

// ##########################################################################
