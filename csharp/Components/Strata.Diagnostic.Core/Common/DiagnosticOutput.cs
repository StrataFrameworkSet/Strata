//  ##########################################################################
//  # File Name: DiagnosticOutput.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Contains the output of running a diagnostic. 
    /// </summary>
    ///  
    public 
    class DiagnosticOutput
    {
        public string                DiagnosticName { get; protected set; }
        public string                Description { get; protected set; }
        public DiagnosticResultState ResultState { get; protected set; }
        public Exception             Exception { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        DiagnosticOutput(
            string                name,
            string                description,
            DiagnosticResultState state,
            Exception             exception)
        {
            DiagnosticName = name;
            Description    = description;
            ResultState    = state;
            Exception      = exception;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool
        HasException()
        {
            return Exception != null;
        }
    }
}

//  ##########################################################################
