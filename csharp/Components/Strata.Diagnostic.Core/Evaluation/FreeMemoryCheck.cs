//  ##########################################################################
//  # File Name: FreeMemoryCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Diagnostics;
using System.Text;
using System.Threading.Tasks;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Evaluation
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Determines if there is a minimum specfied amount of free memory.
    /// </summary>
    ///  
    public 
    class FreeMemoryCheck:
        DiagnosticCheck
    {
        public long FreeMemoryBytesMinimum { get; set; }

        public 
        FreeMemoryCheck(string name):
            base( name )
        {
            FreeMemoryBytesMinimum = 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the the specified connection string can successfully
        /// connect to a database.
        /// </summary>
        /// 
        /// <exception cref="DiagnosticException">
        /// Free memory less than specified minimum
        /// </exception>
        /// 
        protected override Task<string> 
        RunCheck()
        {
		    StringBuilder success   = new StringBuilder();
		    StringBuilder failure   = new StringBuilder();
	
		    if ( !CheckFreeMemory( success,failure ) )
			    throw new DiagnosticException( failure.ToString() );
		
		    return Task.FromResult(success.ToString());          
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        /// <exception cref="DiagnosticException">
        /// Free memory less than specified minimum
        /// </exception>
        /// 
        protected override Task<string> 
        RunRecovery()
        {
		    StringBuilder success = new StringBuilder();
		    StringBuilder failure = new StringBuilder();
		
            GC.Collect();
	
		    if ( !CheckFreeMemory( success,failure ) )
			    throw new DiagnosticException( failure.ToString() );
		
		    return Task.FromResult(success.ToString());          

        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        /// <returns>True - free memory check is recoverable</returns>
        /// 
        protected override bool 
        IsRecoverable()
        {
            return true;
        }
       
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
	    protected bool 
	    CheckFreeMemory(StringBuilder success,StringBuilder failure)
	    {
            PerformanceCounter availableBytes =   
                new PerformanceCounter( "Memory","Available Bytes",true );  
            long freeMemoryBytes = (long)availableBytes.NextValue();  
		
		    if ( freeMemoryBytes >= FreeMemoryBytesMinimum )
		    {
			    success
				    .Append( "Free memory (" )
				    .Append( freeMemoryBytes )
				    .Append( " bytes) is greater or equal to specified minimum ("  )
				    .Append( FreeMemoryBytesMinimum )
				    .Append( " bytes)" );
			    return true;
		    }
		    else
		    {
			    failure
				    .Append( "Free memory (" )
				    .Append( freeMemoryBytes )
				    .Append( " bytes) is less than specified minimum ("  )
				    .Append( FreeMemoryBytesMinimum )
				    .Append( " bytes)" );
			    return false;
		    }
	    }
    }
}

//  ##########################################################################
