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
 * Receives output from <code>TaskGateway</code> method calls.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface TaskOutputReceiver
{
	/************************************************************************
	 * Processes output from <code>TaskGateway.insertNewTask</code>. 
	 *
	 * @param 	task	summary of newly inserted task
	 * 
	 * @see		TaskGateway#insertNewTask(
	 * 				TaskOutputReceiver,
	 * 				TaskRepPacket)
	 */
	public void
	onInsertNewTask(TaskSummaryPacket task);
	
	/************************************************************************
	 * Processes output from <code>TaskGateway.updateExistingTask</code>. 
	 *
	 * @param 	task	summary of updated task	
	 * 
	 * @see		TaskGateway#updateExistingTask(
	 * 				TaskOutputReceiver, 
	 * 				TaskRepPacket)
	 */
	public void 
	onUpdateExistingTask(TaskSummaryPacket task);
	
	/************************************************************************
	 * Processes output from <code>TaskGateway.removeExistingTask</code>. 
	 *
	 * @param 	task	identifier of removed task
	 * 
	 * @see		TaskGateway#removeExistingTask(
	 * 				TaskOutputReceiver, 
	 * 				TaskIdPacket)
	 */
	public void
	onRemoveExistingTask(TaskIdPacket task);
	
	/************************************************************************
	 * Processes output from <code>TaskGateway.getTaskSummaries</code>. 
	 *
	 * @param 	task	list of task summaries
	 * 
	 * @see		TaskGateway#getTaskSummaries(TaskOutputReceiver)
	 */
	public void
	onGetTaskSummaries(TaskSummaryListPacket tasks);
	
	/************************************************************************
	 * Processes output from <code>TaskGateway.getTaskRepresentation</code>. 
	 *
	 * @param 	task	full task information for specified task	
	 * 
	 * @see		TaskGateway#getTaskEnvoy(
	 * 				TaskOutputReceiver, 
	 * 				TaskIdPacket)
	 */
	public void
	onGetTaskEnvoy(TaskRepPacket task);
	
	/************************************************************************
	 * Process exceptions from <code>TaskGateway</code> methods.
	 *
	 * @param 	exception	an exception from <code>TaskGatway</code>
	 * 						method calls.
	 * 
	 * @see		TaskGateway#insertNewTask(
	 * 				TaskOutputReceiver,
	 * 				TaskRepPacket)
	 * @see		TaskGateway#updateExistingTask(
	 * 				TaskOutputReceiver, 
	 * 				TaskRepPacket)
	 * @see		TaskGateway#removeExistingTask(
	 * 				TaskOutputReceiver, 
	 * 				TaskIdPacket)
	 * @see		TaskGateway#getTaskSummaries(TaskOutputReceiver)
	 * @see		TaskGateway#getTaskEnvoy(
	 * 				TaskOutputReceiver, 
	 * 				TaskIdPacket)
	 */
	public void
	onException(Throwable exception);

}


// ##########################################################################
