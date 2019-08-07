//  ##########################################################################
//  # File Name: WpfTestStartupShutdownController.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows;
using Strata.Client.MainClient;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Unity;

namespace Strata.WpfTestApp.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class WpfTestStartupShutdownController:
        IStartupShutdownController
    {
        public IUnityContainer Container { get; set; }

        public
        WpfTestStartupShutdownController() {}

        public void 
        StartUp()
        {
            IMainController controller = Container.Resolve<IMainController>();

            controller.Start();
        }

        public void
        ShutDown()
        {
            IMainController controller = Container.Resolve<IMainController>();

            controller.Stop();
        }
    }
}

//  ##########################################################################
