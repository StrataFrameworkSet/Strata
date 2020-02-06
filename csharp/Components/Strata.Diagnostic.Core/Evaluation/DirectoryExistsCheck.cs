//  ##########################################################################
//  # File Name: DirectoryExistsCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.IO;
using System.Threading.Tasks;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Evaluation
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Determines if a specified directory path exists.
    /// </summary>
    ///  
    public 
    class DirectoryExistsCheck:
        DiagnosticCheck
    {
        public string Path { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DirectoryExistsCheck</c> instance.
        /// </summary>
        /// 
        public 
        DirectoryExistsCheck(string name):
            base( name )
        {
            Path = string.Empty;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified directory path exists.
        /// </summary>
        /// 
        protected override Task<string> 
        RunCheck()
        {
		    if ( !Directory.Exists( Path ) )
			    throw new DiagnosticException( 
				    "Directory " + Path + " does not exist." );
		
		    return Task.FromResult("Directory " + Path + " exists.");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override Task<string> 
        RunRecovery()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override bool 
        IsRecoverable()
        {
            return false;
        }
    }
}

//  ##########################################################################
