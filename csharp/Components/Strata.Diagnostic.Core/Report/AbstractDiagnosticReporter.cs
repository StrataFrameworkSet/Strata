//  ##########################################################################
//  # File Name: AbstractDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Report
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class AbstractDiagnosticReporter:
        IDiagnosticReporter
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        protected 
        AbstractDiagnosticReporter() {}

        public virtual void 
        BeginReport() {}

        public virtual void 
        EndReport() {}

        public virtual void 
        BeginDiagnostic(DiagnosticOutput output) {}

        public virtual void 
        EndDiagnostic(DiagnosticOutput output) {}

        public virtual void 
        ReportBeginFailure(DiagnosticOutput output) {}

        public virtual void 
        ReportCheckSuccess(DiagnosticOutput output) {}

        public virtual void 
        ReportCheckFailure(DiagnosticOutput output) {}

        public virtual void 
        ReportRecoverySuccess(DiagnosticOutput output) {}

        public virtual void 
        ReportRecoveryFailure(DiagnosticOutput output) {}

        public virtual void 
        ReportUnknownFailure(DiagnosticOutput output) {}
    }
}

//  ##########################################################################
