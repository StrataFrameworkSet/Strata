//  ##########################################################################
//  # File Name: DiagnosticOutput.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Diagnostic.Core.Common
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class DiagnosticOutput
    {
        public String                DiagnosticName { get; protected set; }
        public String                Description { get; protected set; }
        public DiagnosticResultState ResultState { get; protected set; }
        public Exception             Exception { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        DiagnosticOutput(
            String                name,
            String                description,
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
