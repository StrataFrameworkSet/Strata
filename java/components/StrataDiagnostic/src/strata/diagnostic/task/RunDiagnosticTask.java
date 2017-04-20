// ##########################################################################
// # File Name:	RunDiagnosticTask.java
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

package strata.diagnostic.task;

import strata.diagnostic.core.IDiagnostic;
import strata.diagnostic.core.IDiagnosticResult;
import strata.foundation.task.AbstractTask;

/****************************************************************************
 * Encapsulates the execution of {@code IDiagnostic}s so they
 * can be scheduled to run at specified times.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class RunDiagnosticTask 
    extends    AbstractTask
	implements Runnable
{
	private final IDiagnostic       itsDiagnostic;
	private final IDiagnosticResult itsResult;
	
	/************************************************************************
	 * Creates a new RunDiagnosticTask. 
	 *
	 * @param diagnostic
	 * @param result
	 */
	public 
	RunDiagnosticTask(IDiagnostic diagnostic,IDiagnosticResult result)
	{
		super( "Run " + diagnostic.getName() );
		itsDiagnostic = diagnostic;
		itsResult     = result;
	}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    execute()
    {
        itsDiagnostic.runDiagnostic( itsResult );        
    }

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public void 
	run()
	{
	    execute();
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public String 
	getName()
	{
		return itsDiagnostic.getName();
	}
}

//##########################################################################
