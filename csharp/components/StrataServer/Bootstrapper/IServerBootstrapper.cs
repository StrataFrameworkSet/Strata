//  ##########################################################################
//  # File Name: IServerBootstrapper.cs
//  # Copyright: 2012, Capital Group Companies, Inc.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Common.Logging;
using Strata.Common.Task;
using Microsoft.Practices.Unity;

namespace Strata.Server.Bootstrapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IServerBootstrapper
    {
        ILogger                    Logger { get; }
        IUnityContainer            Container { get; }
        IList<IServerModule>       Modules { get; }
        IStartupShutdownController StartupShutdownController { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Bootstraps the server's components and then starts the server.
        /// </summary>
        /// 
        /// <param name="factory">server's factory</param>
        /// 
        void 
        Run(IServerFactory factory);
    }
}

//  ##########################################################################
