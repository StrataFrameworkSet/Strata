//  ##########################################################################
//  # File Name: IServerApplication.cs
//  # Copyright: 2014, Capital Group Companies, Inc.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Server.Application
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Server application control interface.
    /// </summary>
    ///  
    public 
    interface IServerApplication
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Starts a server application.
        /// </summary>
        /// 
        void
        Start();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stops a server application.
        /// </summary>
        /// 
        void 
        Stop();
    }
}

//  ##########################################################################
