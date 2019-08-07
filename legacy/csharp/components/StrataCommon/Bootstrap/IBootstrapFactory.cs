//  ##########################################################################
//  # File Name: IBootstrapFactory.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
    interface IBootstrapFactory
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
        IList<IModule> 
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
