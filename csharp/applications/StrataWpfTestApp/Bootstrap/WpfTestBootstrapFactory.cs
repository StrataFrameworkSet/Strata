//  ##########################################################################
//  # File Name: WpfTestBootstrapFactory.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows;
using Strata.Client.Bootstrap;
using Strata.Client.MainClient;
using Strata.Common.Bootstrap;
using Strata.Common.Logging;
using Strata.WpfClient.WpfMainClient;
using Microsoft.Practices.Unity;

namespace Strata.WpfTestApp.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class WpfTestBootstrapFactory:
        AbstractBootstrapFactory,
        IClientBootstrapFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>WpfTestBootstrapFactory</c>.
        /// </summary>
        /// 
        public
        WpfTestBootstrapFactory() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public override IUnityContainer
        CreateContainer()
        {
            IUnityContainer           output = null;

            output = new UnityContainer();
            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public override ILogger 
        CreateLogger()
        {
            return new ConsoleLogger();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override IList<IModule> 
        CreateModules()
        {
            List<IModule> modules = new List<IModule>();

            modules.Add( new WpfMainClientModule());
            return modules;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override IStartupShutdownController 
        CreateStartupShutdownController()
        {
            return new WpfTestStartupShutdownController();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public DependencyObject 
        CreateShell(IUnityContainer container)
        {
            container.RegisterType<IMainView,WpfMainView>(
                new ContainerControlledLifetimeManager());

            return  (WpfMainView)container.Resolve<IMainView>();

        }
    }
}

//  ##########################################################################
