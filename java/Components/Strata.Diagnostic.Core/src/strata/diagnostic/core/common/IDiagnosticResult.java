// ##########################################################################
// # File Name:	IDiagnosticResult.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataInspector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInspector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInspector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.diagnostic.core.common;

import java.util.List;

/**
 * Captures the results of a diagnostic check.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IDiagnosticResult
	extends IDiagnosticReceiver
{
	/************************************************************************
	 *  
	 *
	 * @param reporter
	 */
	public void
	attachReporter(IDiagnosticReporter reporter);
	
	/************************************************************************
	 *  
	 *
	 * @param reporter
	 */
	public void
	detachReporter(IDiagnosticReporter reporter);
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public List<IDiagnosticReporter>
	getReporters();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public boolean
	hasReporters();
	
	/************************************************************************
	 *  
	 *
	 * @param reporter
	 * @return
	 */
	public boolean
	hasReporter(IDiagnosticReporter reporter);
	
	/************************************************************************
	 *  
	 *
	 */
	public void
	beginReports();
	
	/************************************************************************
	 *  
	 *
	 */
	public void
	endReports();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public int
	getNumberOfDiagnostics();

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public int
	getNumberOfAborts();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public int
	getNumberOfSuccesses();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public int
	getNumberOfFailures();
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public List<DiagnosticOutput>
	getContents();
}

//##########################################################################
