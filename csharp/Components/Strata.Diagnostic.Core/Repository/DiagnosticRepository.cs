//  ##########################################################################
//  # File Name: DiagnosticRepository.cs
//  ##########################################################################

using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Linq;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class DiagnosticRepository:
        IDiagnosticRepository
    {
        private readonly IDictionary<string,IDiagnostic> itsDiagnostics;

        public 
        DiagnosticRepository()
        {
            itsDiagnostics = new ConcurrentDictionary<string,IDiagnostic>();
        }

        public IDiagnosticRepository 
        Insert(IDiagnostic diagnostic)
        {
            itsDiagnostics.Add(diagnostic.Name,diagnostic);

            if (diagnostic is DiagnosticSuite)
            {
                DiagnosticSuite suite = (DiagnosticSuite)diagnostic;

                foreach (IDiagnostic d in suite.GetDiagnostics())
                    Insert(d);
            }

            return this;
        }

        public IDiagnosticRepository 
        Remove(IDiagnostic diagnostic)
        {
            if (HasUnique(diagnostic.Name))
                itsDiagnostics.Remove(diagnostic.Name);

            return this;
        }

        public IDiagnosticRepository 
        Clear()
        {
            itsDiagnostics.Clear();
            return this;
        }

        public IDiagnostic 
        GetUnique(string diagnosticName)
        {
            return
                HasUnique(diagnosticName)
                    ? itsDiagnostics[diagnosticName]
                    : null;
        }

        public ICollection<IDiagnostic> 
        GetMatching(Func<IDiagnostic,bool> predicate)
        {
            return
                itsDiagnostics
                    .Values
                    .Where(predicate)
                    .ToList();
        }

        public ICollection<IDiagnostic> 
        GetAll()
        {
            return itsDiagnostics.Values;
        }

        public bool 
        HasUnique(string diagnosticName)
        {
            return itsDiagnostics.ContainsKey(diagnosticName);
        }

        public bool
        HasMatching(Func<IDiagnostic,bool> predicate)
        {
            return
                itsDiagnostics
                    .Values
                    .Any(predicate);
        }
    }
}

//  ##########################################################################
