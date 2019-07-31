//  ##########################################################################
//  # File Name: PrismBootstrapHelper.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System.Windows;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Prism.Logging;
using Microsoft.Practices.Prism.Modularity;
using Microsoft.Practices.Prism.PubSubEvents;
using Microsoft.Practices.Prism.Regions;
using Microsoft.Practices.Prism.UnityExtensions;
using Microsoft.Practices.Prism.UnityExtensions.Regions;
using Microsoft.Practices.ServiceLocation;
using Microsoft.Practices.Unity;

namespace Strata.Client.Bootstrap
{
    using IModule = Strata.Common.Bootstrap.IModule;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class PrismBootstrapHelper:
        UnityBootstrapper
    {
        private IBootstrapper           bootstrapper;
        private IClientBootstrapFactory factory;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        PrismBootstrapHelper(
            IBootstrapper           b,
            IClientBootstrapFactory f)
        {
            bootstrapper = b;
            factory      = f;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override IUnityContainer 
        CreateContainer()
        {
            return bootstrapper.Container;
        }


        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Configurates the application's <c>UnityContainer</c>.
        /// </summary>
        /// 
        protected override void 
        ConfigureContainer()
        {
            UnityContainerExtensions.AddNewExtension<UnityBootstrapperExtension>(this.Container);
            UnityContainerExtensions.RegisterInstance<ILoggerFacade>(this.Container, this.Logger);
            UnityContainerExtensions.RegisterInstance<IModuleCatalog>(this.Container, this.ModuleCatalog);

            base.RegisterTypeIfMissing(typeof (IServiceLocator), typeof (UnityServiceLocatorAdapter), true);
            base.RegisterTypeIfMissing(typeof (IModuleInitializer), typeof (ModuleInitializer), true);
            base.RegisterTypeIfMissing(typeof (IModuleManager), typeof (ModuleManager), true);
            base.RegisterTypeIfMissing(typeof (RegionAdapterMappings), typeof (RegionAdapterMappings), true);
            base.RegisterTypeIfMissing(typeof (IRegionManager), typeof (RegionManager), true);
            base.RegisterTypeIfMissing(typeof (IEventAggregator), typeof (EventAggregator), true);
            base.RegisterTypeIfMissing(typeof (IRegionViewRegistry), typeof (RegionViewRegistry), true);
            base.RegisterTypeIfMissing(typeof (IRegionBehaviorFactory), typeof (RegionBehaviorFactory), true);
            base.RegisterTypeIfMissing(typeof (IRegionNavigationJournalEntry), typeof (RegionNavigationJournalEntry), false);
            base.RegisterTypeIfMissing(typeof (IRegionNavigationJournal), typeof (RegionNavigationJournal), false);
            base.RegisterTypeIfMissing(typeof (IRegionNavigationService), typeof (RegionNavigationService), false);
            base.RegisterTypeIfMissing(typeof (IRegionNavigationContentLoader), typeof (UnityRegionNavigationContentLoader), true);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates the root window of the application.
        /// </summary>
        /// 
        protected override DependencyObject 
        CreateShell()
        {
            return factory.CreateShell( Container );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Adds the modules the make up the application to the 
        /// bootstrapper's module catalog.
        /// </summary>
        /// 
        protected override void 
        ConfigureModuleCatalog()
        {
            ModuleCatalog moduleCatalog = (ModuleCatalog)ModuleCatalog;

            bootstrapper
                .Container
                .RegisterInstance( 
                    typeof(IList<IModule>),
                    factory.CreateModules() );

            base.ConfigureModuleCatalog();
            moduleCatalog.AddModule(typeof(PrismAdapterModule));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override void 
        InitializeModules()
        {
            base.InitializeModules();
        }

    }
}

//  ##########################################################################
