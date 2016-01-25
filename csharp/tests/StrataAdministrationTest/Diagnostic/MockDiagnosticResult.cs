//  ##########################################################################
//  # File Name: MockDiagnosticResult.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Administration.Diagnostic
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class MockDiagnosticResult:
        AbstractDiagnosticResult
    {
        public ISet<String> ActualRun { get; protected set; }

        public 
        MockDiagnosticResult()
        {
            ActualRun = new SortedSet<String>();
        }

        public override void 
        BeginDiagnostic(IDiagnostic d)
        {
            ActualRun.Add( d.Name );
            base.BeginDiagnostic( d );
        }

        public void
        Verify()
        {
            
        }

        public void
        Clear()
        {
            ActualRun.Clear();
        }
    }
}

//  ##########################################################################
