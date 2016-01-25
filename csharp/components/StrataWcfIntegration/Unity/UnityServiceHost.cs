//  ##########################################################################
//  # File Name: UnityServiceHost.cs
//  # Copyright: 2013, Capital Group Companies, Inc.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ServiceModel;
using Microsoft.Practices.Unity;

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
    class UnityServiceHost:
        ServiceHost
    {
        public IUnityContainer Container { get; set; }

        public 
        UnityServiceHost()
        {
            Container = new UnityContainer();
        }

        public 
        UnityServiceHost(Type serviceType,params Uri[] baseAddresses): 
            base(serviceType,baseAddresses)
        {
            Container = new UnityContainer();
        }

        protected override void 
        OnOpening()
        {
            if (Description.Behaviors.Find<UnityServiceBehavior>() == null)
                Description.Behaviors.Add(new UnityServiceBehavior(Container));

            base.OnOpening();
        }

    }
}

//  ##########################################################################
