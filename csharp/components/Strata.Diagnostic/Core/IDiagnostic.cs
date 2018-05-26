//  ##########################################################################
//  # File Name: IDiagnostic.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;

namespace Strata.Diagnostic.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// <para>
    /// Provides an abstraction for performing one or more diagnostic checks
    /// on a running system and recording the results.
    /// </para>
    /// <para>
    /// A diagnostic can check various aspects of a running application
    /// and the environment in which it runs. Examples of basic diagnostics
    /// include:
    /// <list type="bullet">
    /// <item>
    /// Determining if a specified file or directory exists.
    /// </item>
    /// <item>
    /// Checking if a specified amount of free memory is available.
    /// </item>
    /// <item>
    /// Checking if a specified network address is reachable.
    /// </item>
    /// <item>
    /// Checking if a specified database is accessible.</item>
    /// </list>
    /// These can be combined with application specific diagnostic 
    /// checks to form a suite of diagnostics that can verify the
    /// state of an application and report any diagnostic failures.
    /// </para>
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IDiagnostic
    {
        String Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Runs the diagnostic(s) and records the results.
        /// </summary>
        /// 
        /// <param name="result">recorded diagnostics result</param>
        /// 
        void
        RunDiagnostic(IDiagnosticResult result);
    }
}

//  ##########################################################################
