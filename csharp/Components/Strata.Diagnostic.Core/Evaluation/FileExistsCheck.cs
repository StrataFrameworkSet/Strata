//  ##########################################################################
//  # File Name: FileExistsCheck.cs
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
    /// Determines if a specified file path exists.
    /// </summary>
    ///  
    public 
    class FileExistsCheck:
        DiagnosticCheck
    {
        public string Path { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DirectoryExistsCheck</c> instance.
        /// </summary>
        /// 
        public 
        FileExistsCheck(string name):
            base( name )
        {
            Path = String.Empty;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified directory path exists.
        /// </summary>
        /// 
        protected override Task<string> 
        RunCheck()
        {
		    if ( !File.Exists( Path ) )
			    throw new DiagnosticException( 
				    "File " + Path + " does not exist." );
		
		    return Task.FromResult("File " + Path + " exists.");
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
