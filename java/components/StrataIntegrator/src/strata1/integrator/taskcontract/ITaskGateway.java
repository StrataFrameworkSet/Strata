// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
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

package strata1.integrator.taskcontract;

/**
 * <a href="http://martinfowler.com/eaaCatalog/gateway.html">Gateway</a> 
 * for managing tasks that are executed by the system.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ITaskGateway
{
	/************************************************************************
	 * Inserts a new task into the system. 
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ITaskGateway</code> method calls.
	 * @param input		new task information 
	 * 
	 * @see				ITaskOutputReceiver#onInsertNewTask(
	 * 						TaskSummaryPacket)
	 */
	public void
	insertNewTask(
		ITaskOutputReceiver 	output,
		TaskRepPacket input);
	
	/************************************************************************
	 * Updates an existing task in the system.
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ITaskGateway</code> method calls.
	 * @param input		updated task information
	 * 
	 * @see 			ITaskOutputReceiver#onUpdateExistingTask(
	 * 						TaskSummaryPacket)
	 */
	public void
	updateExistingTask(
		ITaskOutputReceiver 	output,
		TaskRepPacket input);
	
	/************************************************************************
	 * Removes an existing task from the system.  
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ITaskGateway</code> method calls.
	 * @param input		identifier of task to be removed
	 * 
	 * @see 			ITaskOutputReceiver#onRemoveExistingTask(
	 * 						TaskIdPacket)
	 */
	public void
	removeExistingTask(ITaskOutputReceiver output,TaskIdPacket input);
	
	/************************************************************************
	 * Retrieves summarary information for all of the tasks in the system.
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ITaskGateway</code> method calls.
	 * 
	 * @see 			ITaskOutputReceiver#onGetTaskSummaries(
	 * 						TaskSummaryListPacket)
	 */
	public void
	getTaskSummaries(ITaskOutputReceiver output);
	
	/************************************************************************
	 * Retrieves the full task information for the specified task. 
	 *
	 * @param output	receives output (possibly asynchronous) from 
	 * 					<code>ITaskGateway</code> method calls.
	 * @param input		identifier of task to be retrieved
	 * 
	 * @see 			ITaskOutputReceiver#onGetTaskEnvoy(TaskRepPacket)
	 */
	public void
	getTaskEnvoy(ITaskOutputReceiver output,TaskIdPacket input);
}


// ##########################################################################
