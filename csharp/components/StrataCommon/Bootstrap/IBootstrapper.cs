//  ##########################################################################
//  # File Name: IBootstrapper.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Common.Logging;
using Microsoft.Practices.Unity;

namespace Strata.Common.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    interface IBootstrapper
    {
        ILogger                    Logger { get; }
        IUnityContainer            Container { get; }
        IList<IModule>       Modules { get; }
        IStartupShutdownController StartupShutdownController { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Bootstraps the server's components and then starts the server.
        /// </summary>
        /// 
        /// <param name="factory">server's factory</param>
        /// 
        void 
        Run(IBootstrapFactory factory);
    }
}

//  ##########################################################################
