//  ##########################################################################
//  # File Name: UnityServiceBehavior.cs
//  # Copyright: 2013, Capital Group Companies, Inc.
//  ##########################################################################

using Microsoft.Practices.Unity;
using System.Collections.ObjectModel;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;

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
    class UnityServiceBehavior:
        IServiceBehavior
    {
        public UnityInstanceProvider InstanceProvider { get; set; }

        private ServiceHost          serviceHost;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>UnityServiceBehavior</c> instance.
        /// </summary>
        /// 
        public 
        UnityServiceBehavior()
        {
            InstanceProvider = new UnityInstanceProvider();
            serviceHost      = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>UnityServiceBehavior</c> instance.
        /// </summary>
        /// 
        public 
        UnityServiceBehavior(IUnityContainer container)
        {
            InstanceProvider = new UnityInstanceProvider();
            InstanceProvider.Container = container;
            serviceHost = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        Validate(
            ServiceDescription serviceDescription,
            ServiceHostBase    serviceHostBase) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        AddBindingParameters(
            ServiceDescription          serviceDescription,
            ServiceHostBase             serviceHostBase,
            Collection<ServiceEndpoint> endpoints,
            BindingParameterCollection  bindingParameters) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        ApplyDispatchBehavior(
            ServiceDescription serviceDescription,
            ServiceHostBase    serviceHostBase)
        {
            foreach (
                ChannelDispatcherBase cdb in 
                    serviceHostBase.ChannelDispatchers)
            {
                ChannelDispatcher cd = cdb as ChannelDispatcher;

                if (cd != null)
                {
                    foreach (EndpointDispatcher endpoint in cd.Endpoints)
                    {
                        InstanceProvider.ServiceType = 
                            serviceDescription.ServiceType;
                        endpoint
                            .DispatchRuntime
                            .InstanceProvider = InstanceProvider;
                    }
                }
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public void 
        AddToHost(ServiceHost host)
        {
            // only add to host once
            if (serviceHost != null) return;
            host.Description.Behaviors.Add(this);
            serviceHost = host;
        }

    }
}

//  ##########################################################################
