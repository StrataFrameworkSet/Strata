using System;
using System.Windows;
using Strata.Client.Bootstrap;
using Strata.Common.Bootstrap;
using Strata.WpfTestApp.Bootstrap;

namespace Strata.WpfTestApp.Main
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Interaction logic for WpfTestApp.xaml
    /// </summary>
    /// 
    public partial 
    class WpfTestApp: 
        Application
    {
        private IClientBootstrapFactory factory;
        private IBootstrapper           bootstrapper;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Starts the <c>WpfTestApp</c> by creating and  
        /// running the <c>ClientBootstrapper</c>. 
        /// </summary>
        /// 
        protected override void 
        OnStartup(StartupEventArgs e)
        {
            factory      = new WpfTestBootstrapFactory();
            bootstrapper = new ClientBootstrapper();

            Console.WriteLine("debug 1");
            bootstrapper.Run( factory );
            Console.WriteLine("debug 2");
            bootstrapper.StartupShutdownController.StartUp();
            Console.WriteLine("debug 3");
        }
    }
}
