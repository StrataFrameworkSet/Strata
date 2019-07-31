//  ##########################################################################
//  # File Name: AbstractDiagnostic.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class for all <c>IDiagnostic</c>s.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract 
    class AbstractDiagnostic: 
        IDiagnostic
    {
        public String Name { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractDiagnostic</c> instance.
        /// </summary>
        /// 
        protected 
        AbstractDiagnostic(String name)
        {
            Name = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IDiagnostic.RunDiagnostic(IDiagnosticResult)"/>
        /// </summary>
        /// 
        public abstract void 
        RunDiagnostic(IDiagnosticResult result);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subclasses override this method to implement mechanisms for 
        /// transitioning the subject of the diagnostic (e.g. a subsystem, 
        /// component, or other resource) from its "normal" operating mode
        /// to a "diagnostic" mode in which the diagnostic can be run safely
        /// without compromising the correctness of the enclosing system.
        /// </summary>
        /// 
        /// <exception cref="DiagnosticAbortedException">
        /// Indicates that the subject of the diagnostic could not  
        /// safely transition to its diagnostic mode.
        /// </exception>
        /// 
        protected virtual void
        BeginDiagnosticMode() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subclasses override this method to implement mechanisms for
        /// transitioning the subject of the diagnostic (e.g. a subsystem,
        /// component, or other resource) from its "diagnostic" mode back
        /// to its "normal" operating mode.
        /// </summary>
        /// 
        protected virtual void
        EndDiagnosticMode() {}

    }
}

//  ##########################################################################
