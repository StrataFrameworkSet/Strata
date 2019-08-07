//  ##########################################################################
//  # File Name: TextWriterDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.IO;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class for <c>IDiagnosticReporter</c>s that output
    /// string based messages for reporting purposes.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class TextWriterDiagnosticReporter:
        AbstractDiagnosticReporter
    {
        private readonly TextWriter writer;


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractMessageBasedDiagnosticReporter</c>
        /// instance.
        /// </summary>
        /// 
        public 
        TextWriterDiagnosticReporter():
            this( Console.Out ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractMessageBasedDiagnosticReporter</c>
        /// instance.
        /// </summary>
        /// 
        public 
        TextWriterDiagnosticReporter(TextWriter w)
        {
            writer = w;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see 
        /// cref="IDiagnosticReporter.EndDiagnostic(DiagnosticOutput)"/>
        /// </summary>
        /// 
        public override void 
        EndDiagnostic(DiagnosticOutput output)
        {
            switch (output.ResultState)
            {
                case DiagnosticResultState.SUCCEEDED:
                    writer.Write(
                        "Succeeded:  [{0}] {1}\n\n",
                        output.DiagnosticName,
                        output.Description );
                    break;

                case DiagnosticResultState.RECOVERED:
                    writer.Write(
                        "Recovered:  [{0}] {1}\n\n",
                        output.DiagnosticName,
                        output.Description );
                    break;

                case DiagnosticResultState.ABORTED:
                    writer.Write(
                        "Aborted: [{0}] {1} {2}\n\n",
                        output.DiagnosticName,
                        output.Description,
                        output.Exception.Message );
                    break;

                case DiagnosticResultState.FAILED:
                    writer.Write(
                        "Failed:  [{0}] {1} {2}\n\n",
                        output.DiagnosticName,
                        output.Description,
                        output.Exception.Message );
                    break;

                default:
                    break;
            }

            writer.Flush();
        }
    }
}

//  ##########################################################################
