//  ##########################################################################
//  # File Name: Bootstrapper.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
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
    class Bootstrapper:
        IBootstrapper
    {
        public ILogger                    Logger { get; protected set; }
        public IUnityContainer            Container { get; protected set; }
        public IList<IModule>             Modules { get; protected set; }
        public IStartupShutdownController StartupShutdownController{get;protected set;}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        Bootstrapper() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual void 
        Run(IBootstrapFactory factory)
        {
            try
            {
                CreateContainer(factory);
                CreateLogger(factory);
                CreateModules(factory);
                CreateStartupShutdownController(factory);
                ConfigureStartupShutdownController();
                InitializeModules();
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
        CreateContainer(IBootstrapFactory factory)
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
        CreateLogger(IBootstrapFactory factory)
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
        CreateModules(IBootstrapFactory factory)
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
        CreateStartupShutdownController(IBootstrapFactory factory)
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

            foreach (IModule module in Modules)
            {
                Logger.Verbose("Initalizing module: " + module.Name + ".");
                module.Initialize(Container);
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
            Container.RegisterInstance<IStartupShutdownController>( 
                StartupShutdownController );
        }
    }
}

//  ##########################################################################
