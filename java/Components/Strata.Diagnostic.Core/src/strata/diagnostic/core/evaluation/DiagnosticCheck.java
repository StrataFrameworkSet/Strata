// ##########################################################################
// # File Name:	DiagnosticCheck.java
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

package strata.diagnostic.core.evaluation;

import strata.diagnostic.core.common.*;

/**
 * Analyzes running applications, discovers problems with application state
 * or processing logic, recovers from these problems if possible, and 
 * reports diagnostic results.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class DiagnosticCheck 
	extends AbstractDiagnostic
{
	/************************************************************************
	 * Creates a new DiagnosticCheck. 
	 *
	 * @param name The name of the diagnostic check.
	 */
	public 
	DiagnosticCheck(String name)
	{
		super( name );
	}

	/************************************************************************
	 * {@inheritDoc}
	 * <p>
	 * This method is a <b>Template Method</b> that gets instantiated with 
	 * functionality from subclasses.
	 * @see IDiagnostic#runDiagnostic(IDiagnosticResult)
	 */

	public void 
	runDiagnostic(IDiagnosticResult result)
	{
        try
        {
    		result.beginDiagnostic( this );
        	beginDiagnosticMode();
            result.reportCheckSuccess( this,runCheck() );
        }
        catch (DiagnosticAbortedException ae)
        {
        	result.reportBeginFailure( this,ae );
        }
        catch (DiagnosticException ce)
        {
            result.reportCheckFailure( this,ce );

            if ( isRecoverable() )
            {
                try
                {
                    result.reportRecoverySuccess( this,runRecovery() );
                }
                catch (DiagnosticException re)
                {
                    result.reportRecoveryFailure( this,re );
                }
                catch (Exception ue)
                {
                	result.reportUnknownFailure( this,ue );
                }
            }
        }
        catch (Exception ue)
        {
            result.reportUnknownFailure( this,ue );
        }
        finally
        {
        	endDiagnosticMode();
        	result.endDiagnostic( this );
        }
	}

	/************************************************************************
	 * Subclasses override this method to implement 
	 * diagnostic checking mechanisms. 
	 *
	 * @return	The result message of the diagnostic check.
	 * @throws	DiagnosticException	Indicates that the diagnostic has 
	 * 			discovered a problem or error during its check.
	 */
	protected abstract String 
	runCheck() throws DiagnosticException;
	
	/************************************************************************
	 * Subclasses override this method to implement 
	 * diagnostic recovery mechanisms if applicable. 
	 *
	 * @return	The result message of the diagnostic recovery.
	 * @throws 	DiagnosticException Indicates that the diagnostic has 
	 * 			discovered a problem or error during its recovery.
	 */
	protected abstract String 
	runRecovery() throws DiagnosticException;
	
	/************************************************************************
	 * Determines if the DiagnosticCheck can recover if it discovers
	 * an problem or error. 
	 *
	 * @return	Returns true if the DiagnosticCheck has a  
	 * 			recovery mechanism.
	 */
	protected abstract boolean 
	isRecoverable();
}

//##########################################################################
