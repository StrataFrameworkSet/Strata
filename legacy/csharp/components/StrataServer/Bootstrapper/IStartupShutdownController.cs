//  ##########################################################################
//  # File Name: IServerStartupController.cs
//  # Copyright: 2013, Capital Group Companies, Inc.
//  ##########################################################################

using Microsoft.Practices.Unity;

namespace Strata.Server.Bootstrapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Controls a server's startup process.
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IStartupShutdownController
    {
        IUnityContainer Container { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Performs the steps to start up a server.
        /// </summary>
        /// 
        void
        StartUp();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Performs the steps to shut down a server.
        /// </summary>
        /// 
        void
        ShutDown();
    }
}

//  ##########################################################################
