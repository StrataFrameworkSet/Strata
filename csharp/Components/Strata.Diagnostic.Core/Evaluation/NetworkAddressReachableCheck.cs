//  ##########################################################################
//  # File Name: NetworkAddressReachableCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Net;
using System.Net.NetworkInformation;
using System.Threading.Tasks;
using Strata.Diagnostic.Core.Common;

namespace Strata.Diagnostic.Core.Evaluation
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Determines if a specified network address is reachable.
    /// </summary>
    ///  
    public 
    class NetworkAddressReachableCheck:
        DiagnosticCheck
    {
        public IPAddress NetworkAddress { get; set; }

        public 
        NetworkAddressReachableCheck(string name):
            base( name )
        {
            NetworkAddress = null;
        }

        protected override async Task<string> 
        RunCheck()
        {
            try
            {
                Ping      pinger = new Ping();
                PingReply reply = await pinger.SendPingAsync( NetworkAddress );

                if ( reply != null && reply.Status == IPStatus.Success )
				    return "Network address " + NetworkAddress + " is reachable.";
		    }
		    catch (Exception e)
		    {
			    throw new DiagnosticException( "ping failed",e );
		    }
	    
			throw 
				new DiagnosticException( 
					"Network address " + NetworkAddress + " is not reachable." );	
        }

        protected override Task<string> 
        RunRecovery()
        {
            throw new NotImplementedException();
        }

        protected override bool 
        IsRecoverable()
        {
            return false;
        }
    }
}

//  ##########################################################################
