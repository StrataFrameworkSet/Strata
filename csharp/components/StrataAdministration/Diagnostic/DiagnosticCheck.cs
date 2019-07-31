//  ##########################################################################
//  # File Name: DiagnosticCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Analyzes running applications, discovers problems with application 
    /// state or processing logic, recovers from these problems if possible, 
    /// and reports diagnostic results.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class DiagnosticCheck:
        AbstractDiagnostic
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DiagnosticCheck</c> instance.
        /// </summary>
        /// 
        protected 
        DiagnosticCheck(String name):
            base( name ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnostic.RunDiagnostic(IDiagnosticResult)"/>
        /// This method is a 
        /// <a href="http://en.wikipedia.org/wiki/Template_method_pattern">
        /// Template Method</a> that gets instantiated with functionality 
        /// from subclasses.
        /// </summary>
        /// 
	    public override void 
	    RunDiagnostic(IDiagnosticResult result)
	    {
            try
            {
    		    result.BeginDiagnostic( this );
        	    BeginDiagnosticMode();
                result.ReportCheckSuccess( this,RunCheck() );
            }
            catch (DiagnosticAbortedException ae)
            {
        	    result.ReportBeginFailure( this,ae );
            }
            catch (DiagnosticException ce)
            {
                result.ReportCheckFailure( this,ce );

                if ( IsRecoverable() )
                {
                    try
                    {
                        result.ReportRecoverySuccess( this,RunRecovery() );
                    }
                    catch (DiagnosticException re)
                    {
                        result.ReportRecoveryFailure( this,re );
                    }
                    catch (Exception ue)
                    {
                	    result.ReportUnknownFailure( this,ue );
                    }
                }
            }
            catch (Exception ue)
            {
                result.ReportUnknownFailure( this,ue );
            }
            finally
            {
        	    EndDiagnosticMode();
        	    result.EndDiagnostic( this );
            }
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subclasses override this method to implement 
        /// diagnostic checking mechanisms.
        /// </summary>
        /// 
        /// <returns>The result message of the diagnostic check.</returns>
        /// <exception cref="DiagnosticException">
        /// Indicates that the diagnostic has discovered a problem 
        /// or error during its check.
        /// </exception>
        /// 
        protected abstract String
        RunCheck();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subclasses override this method to implement  
        /// diagnostic recovery mechanisms if applicable.
        /// </summary>
        /// 
        /// <returns>The result message of the diagnostic recovery.</returns>
        /// <exception cref="DiagnosticException">
        /// Indicates that the diagnostic has discovered a problem 
        /// or error during its recovery.
        /// </exception>
        /// 
        protected abstract String
        RunRecovery();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the DiagnosticCheck can recover if 
        /// it discovers an problem or error.
        /// </summary>
        /// 
        /// <returns>
        /// True if the <c>DiagnosticCheck</c> has 
        /// a recovery mechanism.
        /// </returns>
        /// 
        protected abstract bool 
        IsRecoverable();
    }
}

//  ##########################################################################
