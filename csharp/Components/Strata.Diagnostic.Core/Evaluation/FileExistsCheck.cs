//  ##########################################################################
//  # File Name: FileExistsCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.IO;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Evaluation
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
        protected override string 
        RunCheck()
        {
		    if ( !File.Exists( Path ) )
			    throw new DiagnosticException( 
				    "File " + Path + " does not exist." );
		
		    return "File " + Path + " exists.";
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override string 
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
