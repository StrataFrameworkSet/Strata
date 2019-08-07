//  ##########################################################################
//  # File Name: IServerStartupController.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Microsoft.Practices.Unity;

namespace Strata.Common.Bootstrap
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
