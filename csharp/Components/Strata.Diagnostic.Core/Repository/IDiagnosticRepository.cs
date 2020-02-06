//  ##########################################################################
//  # File Name: IDiagnosticRepository.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IDiagnosticRepository
    {
        IDiagnosticRepository
        Insert(IDiagnostic diagnostic);

        IDiagnosticRepository
        Remove(IDiagnostic diagnostic);

        IDiagnosticRepository
        Clear();

        IDiagnostic
        GetUnique(string diagnosticName);

        ICollection<IDiagnostic> 
        GetMatching(Func<IDiagnostic,bool> predicate);

        ICollection<IDiagnostic>
        GetAll();

        bool
        HasUnique(string diagnosticName);

        bool
        HasMatching(Func<IDiagnostic,bool> predicate);
    }
}

//  ##########################################################################
