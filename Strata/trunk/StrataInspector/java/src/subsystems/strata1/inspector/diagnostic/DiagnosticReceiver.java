// ##########################################################################
// # File Name:	AbstractDiagnostic.java
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

package strata1.inspector.diagnostic;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface DiagnosticReceiver
{
	/************************************************************************
	 * Captures the beginning of a diagnostic. 
	 *
	 * @param d
	 */
	public void 
	beginDiagnostic(Diagnostic d);
	
	/************************************************************************
	 * Captures the completion of a diagnostic.
	 *
	 * @param d
	 */
	public void 
	endDiagnostic(Diagnostic d);
	
	/************************************************************************
	 * Captures failure of a diagnostic to begin running. 
	 *
	 * @param d
	 * @param e
	 */
	public void 
	reportBeginFailure(Diagnostic d,DiagnosticAbortedException e);
	
	/************************************************************************
	 * Captures success of a diagnostic check. 
	 *
	 * @param d
	 * @param msg
	 */
	public void 
	reportCheckSuccess(Diagnostic d,String msg);
	
	/************************************************************************
	 * Captures failure of a diagnostic check. 
	 *
	 * @param d
	 * @param e
	 */
	public void 
	reportCheckFailure(Diagnostic d,DiagnosticException e);
	
	/************************************************************************
	 * Captures success of a diagnostic recovery. 
	 *
	 * @param d
	 * @param msg
	 */
	public void 
	reportRecoverySuccess(Diagnostic d,String msg);
	
	/************************************************************************
	 * Captures failure of a diagnostic recovery. 
	 *
	 * @param d
	 * @param e
	 */
	public void 
	reportRecoveryFailure(Diagnostic d,DiagnosticException e);
	
	/************************************************************************
	 * Captures an unknown failure (Exception) encountered during
	 * a diagnostic check or recovery. 
	 *
	 * @param d
	 * @param e
	 */
	public void 
	reportUnknownFailure(Diagnostic d,Exception e);

}


// ##########################################################################
