//  ##########################################################################
//  # File Name: DirectoryExistsCheck.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Administration.Diagnostic;
using System;
using System.IO;

namespace Strata.Administration.BasicDiagnosticCheck
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class DirectoryExistsCheck:
        DiagnosticCheck
    {
        public String Path { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DirectoryExistsCheck</c> instance.
        /// </summary>
        /// 
        public 
        DirectoryExistsCheck(String name):
            base( name )
        {
            Path = String.Empty;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if the specified directory path exists.
        /// </summary>
        /// 
        protected override String 
        RunCheck()
        {
		    if ( !Directory.Exists( Path ) )
			    throw new DiagnosticException( 
				    "Directory " + Path + " does not exist." );
		
		    return "Directory " + Path + " exists.";
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This diagnostic check does not perform recovery.
        /// </summary>
        /// 
        protected override String 
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
