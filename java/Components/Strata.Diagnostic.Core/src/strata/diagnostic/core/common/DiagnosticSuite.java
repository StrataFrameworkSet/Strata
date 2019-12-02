// ##########################################################################
// # File Name:	DiagnosticSuite.java
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

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Represents a suite of {@code IDiagnostic}s that are meant to be 
 * run as a whole or as part of a larger suite. {@code DiagnosticSuite} 
 * is a <a href="{@docRoot}/designpatterns/pat4c.htm">Composite</a> of 
 * {@code IDiagnostic}s.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DiagnosticSuite 
	extends AbstractDiagnostic
{
	private Map<String,IDiagnostic> itsDiagnostics;
	
	/************************************************************************
	 * Creates a new DiagnosticSuite. 
	 *
	 * @param name	The name of the suite of diagnostics.
	 */
	public 
	DiagnosticSuite(String name)
	{
		super( name );
		itsDiagnostics = new LinkedHashMap<String,IDiagnostic>();
	}

	/************************************************************************
	 * {@inheritDoc}
	 * @return
	 */
	@Override
	public CompletionStage<IDiagnosticResult>
	runDiagnostic(IDiagnosticResult result)
	{
		return
			CompletableFuture.supplyAsync(
				() ->
				{
					try
					{
						List<CompletionStage<?>> results =
							new ArrayList<>();

						result.beginDiagnostic(this);
						beginDiagnosticMode();

						itsDiagnostics
							.values()
							.stream()
							.forEach(
								d -> results.add(d.runDiagnostic(result)));

						CompletableFuture
							.allOf(
								results.toArray(
									new CompletableFuture<?>[results.size()]))
							.get();
					}
					catch (DiagnosticAbortedException ae)
					{
						result.reportBeginFailure(this,ae);
					}
					catch (Exception e)
					{
						result.reportUnknownFailure(this,e);
					}
					finally
					{
						endDiagnosticMode();
						result.endDiagnostic(this);
					}

					return result;
				});
	}

	/************************************************************************
	 * Adds a IDiagnostic to the DiagnosticSuite. 
	 *
	 * @param 			d	The diagnostic being added.
	 * @precondition	this.hasDiagnostic( d.getName() ) == false
	 * @postcondition	this.hasDiagnostic( d.getName() ) == true
	 * @postcondition	this.getDiagnostic( d.getName() ) == d
	 */
	public void 
	addDiagnostic(IDiagnostic d)
	{
		itsDiagnostics.put( d.getName(),d );
	}
	
	/************************************************************************
	 * Removes the IDiagnostic with the specified 
	 * name from the DiagnosticSuite. 
	 *
	 * @param 			name	The name of the diagnostic being removed 
	 * 							from the suite.
	 * @precondition	this.hasDiagnostic( name ) == true
	 * @postcondition	this.hasDiagnostic( name ) == false
	 */
	public void 
	removeDiagnostic(String name)
	{
		itsDiagnostics.remove( name );
	}
	
	/************************************************************************
	 * Returns the IDiagnostic associated with the specified name. 
	 *
	 * @param 	name	The name of the diagnostic to be returned.
	 * @return	The diagnostic associated with name.
	 */
	public IDiagnostic 
	getDiagnostic(String name)
	{
		return itsDiagnostics.get( name );
	}

	/************************************************************************
	 * Returns all IDiagnostics contained in this suite.
	 *
	 * @return	All diagnostics contained in this suite.
	 */
	public Collection<IDiagnostic>
	getDiagnostics()
	{
		return itsDiagnostics.values();
	}

	/************************************************************************
	 * Queries the DiagnosticSuite if it contains a IDiagnostic 
	 * with the specified name. 
	 *
	 * @param 	name	The name of the diagnostic being queried.
	 * @return	True if the suite contains a diagnostic with the 
	 * 			specified name.
	 */
	public boolean 
	hasDiagnostic(String name)
	{
		return itsDiagnostics.containsKey( name );
	}
}

//##########################################################################
