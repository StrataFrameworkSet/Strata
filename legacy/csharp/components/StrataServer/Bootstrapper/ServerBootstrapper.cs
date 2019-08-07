//  ##########################################################################
//  # File Name: AbstractServerBootstrapper.cs
//  # Copyright: 2012, Capital Group Companies, Inc.
//  ##########################################################################

using System;
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
    class ServerBootstrapper:
        IServerBootstrapper
    {
        public ILogger                    Logger { get; protected set; }
        public IUnityContainer            Container { get; protected set; }
        public IList<IServerModule>       Modules { get; protected set; }
        public IStartupShutdownController StartupShutdownController{get;protected set;}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        ServerBootstrapper() {}

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IServerBootstrapper.Run(IServerFactory)"/>
        /// 
        public virtual void 
        Run(IServerFactory factory)
        {
            try
            {
                CreateContainer(factory);
                CreateLogger(factory);
                CreateModules(factory);
                CreateStartupShutdownController(factory);
                InitializeModules();
                ConfigureStartupShutdownController();
            }
            catch (Exception e)
            {
                if ( Logger != null )
                    Logger.Error(e.Message);

                throw e;
            }   
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        CreateContainer(IServerFactory factory)
        {
            Container = factory.CreateContainer();

            if ( Container == null )
                throw new NullReferenceException("container is null");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        CreateLogger(IServerFactory factory)
        {
            Logger = factory.CreateLogger();

            if ( Logger == null )
                throw new NullReferenceException("logger is null");

            Logger.Verbose("Registering logger with container.");
            Container.RegisterInstance<ILogger>(Logger);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        CreateModules(IServerFactory factory)
        {
            Logger.Verbose("Creating server modules.");
            Modules = factory.CreateModules();

            if ( Modules == null )
                throw new NullReferenceException("modules is null");

            if ( Modules.Count == 0 )
                throw new InvalidOperationException("no modules were created");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        CreateStartupShutdownController(IServerFactory factory)
        {
            Logger.Verbose("Creating server startup controller.");
            StartupShutdownController = 
                factory.CreateStartupShutdownController();

            if ( StartupShutdownController == null )
                throw 
                    new NullReferenceException(
                        "startup-shutdown controller is null");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        InitializeModules()
        {
            Logger.Verbose(
                "\n" +                
                "*******************************************************\n" + 
                "************* Initializing server modules *************\n" +
                "*******************************************************\n");

            foreach (IServerModule module in Modules)
            {
                Logger.Verbose("Initalizing module: " + module.Name + ".");
                module.Initialize(this);
            }   
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual void 
        ConfigureStartupShutdownController()
        {
            StartupShutdownController.Container = Container;
        }
    }
}

//  ##########################################################################
