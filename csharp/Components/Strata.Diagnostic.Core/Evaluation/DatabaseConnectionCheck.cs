//  ##########################################################################
//  # File Name: DatabaseConnectionCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Diagnostic.Core.Common;
using System;
using System.Data;
using System.Data.SqlClient;

namespace Strata.Diagnostic.Core.Evaluation
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class DatabaseConnectionCheck:
        DiagnosticCheck
    {
        public string ConnectionString { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>DatabaseConnectionCheck</c> instance.
        /// </summary>
        /// 
        public 
        DatabaseConnectionCheck(String name):
            base( name )
        {
            ConnectionString = String.Empty;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the the specified connection string can successfully
        /// connect to a database.
        /// </summary>
        /// 
        protected override string 
        RunCheck()
        {
		    try
		    {
			   SqlConnection connection = 
                    new SqlConnection(ConnectionString);

                connection.Open();
                
                if ( connection.State == ConnectionState.Open )
                {
                    try
                    {
                        connection.Close();
                    }
                    catch(SqlException) {}

                    return 
                        "Connected using connection string: " + 
                        ConnectionString;
                }
                
                throw 
                    new DiagnosticException( 
                        "Failed to connect using connection string: " + 
                        ConnectionString );

		    }
		    catch (Exception e)
		    {
			    throw new DiagnosticException( "connection failed",e );
		    }        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Not implemented because <c>DatabaseConnectionCheck</c> 
        /// is not recoverable.
        /// </summary>
        /// 
        /// <exception cref="NotImplementedException">
        /// <c>DatabaseConnectionCheck</c> is not recoverable.
        /// </exception>
        /// 
        protected override string 
        RunRecovery()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <c>DatabaseConnectionCheck</c> is not recoverable.
        /// </summary>
        /// 
        /// <returns>False - because check is not recoverable</returns>
        /// 
        protected override bool 
        IsRecoverable()
        {
            return false;
        }
    }
}

//  ##########################################################################
