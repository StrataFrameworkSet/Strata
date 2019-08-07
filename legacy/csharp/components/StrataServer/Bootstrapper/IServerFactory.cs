//  ##########################################################################
//  # File Name: IServerFactory.cs
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
    interface IServerFactory
    {

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates and returns the server's object container.
        /// </summary>
        /// 
        /// <returns>server's object container</returns>
        /// 
        IUnityContainer 
        CreateContainer();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates and returns the server's logger.
        /// </summary>
        /// 
        /// <returns>server's logger</returns>
        /// 
        ILogger 
        CreateLogger();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates and returns the list of modules that make up the server.
        /// </summary>
        /// 
        /// <returns>list of modules</returns>
        /// 
        IList<IServerModule> 
        CreateModules();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates and returns the server's startup controller.
        /// </summary>
        /// 
        /// <returns>startup controller</returns>
        /// 
        IStartupShutdownController
        CreateStartupShutdownController();

    }
}

//  ##########################################################################
