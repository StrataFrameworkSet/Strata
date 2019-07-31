//  ##########################################################################
//  # File Name: NetworkAddressReachableCheck.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Diagnostic.Core;
using System;
using System.Net;
using System.Net.NetworkInformation;

namespace Strata.Diagnostic.Network
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class NetworkAddressReachableCheck:
        DiagnosticCheck
    {
        public IPAddress NetworkAddress { get; set; }

        public 
        NetworkAddressReachableCheck(String name):
            base( name )
        {
            NetworkAddress = null;
        }

        protected override String 
        RunCheck()
        {
            try
            {
                Ping      pinger = new Ping();
                PingReply reply = pinger.Send( NetworkAddress );
                    
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

        protected override String 
        RunRecovery()
        {
            return null;
        }

        protected override bool 
        IsRecoverable()
        {
            return false;
        }
    }
}

//  ##########################################################################
