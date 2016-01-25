//  ##########################################################################
//  # File Name: UnityServiceHostFactory.cs
//  # Copyright: 2013, Capital Group Companies, Inc.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Configuration;
using System.ServiceModel;
using System.ServiceModel.Activation;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.Configuration;

namespace Capgroup.Xwing.WcfIntegration.Unity
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class UnityServiceHostFactory:
        ServiceHostFactory
    {
        public IUnityContainer Container { get; protected set; }

        public 
        UnityServiceHostFactory():
            this( null ) {}

        public 
        UnityServiceHostFactory(IUnityContainer container):
            base()
        {
            Container = container;
        }

        protected override ServiceHost 
        CreateServiceHost(Type serviceType,Uri[] baseAddresses)
        {
            UnityServiceHost          serviceHost = null;
            UnityConfigurationSection section     = null;

            serviceHost = new UnityServiceHost(serviceType,baseAddresses);
            serviceHost.Container = Container ?? new UnityContainer();
            section = (UnityConfigurationSection)ConfigurationManager.GetSection("unity");
            section.Configure(serviceHost.Container);

            return serviceHost;
        }
    }
}

//  ##########################################################################
