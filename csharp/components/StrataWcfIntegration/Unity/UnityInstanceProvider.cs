//  ##########################################################################
//  # File Name: UnityInstanceProvider.cs
//  # Copyright: 2013, Capital Group Companies, Inc.
//  ##########################################################################
 
using System;
using System.ServiceModel;
using System.ServiceModel.Channels;
using System.ServiceModel.Dispatcher;
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
    class UnityInstanceProvider: 
        IInstanceProvider
    {
        public Type            ServiceType { set; get; }
        public IUnityContainer Container { set; get; }

        public 
        UnityInstanceProvider(): 
            this(null)
        {
        }

        public 
        UnityInstanceProvider(Type type)
        {
            ServiceType = type;
            Container = new UnityContainer();
        }

        public Object 
        GetInstance(InstanceContext instanceContext,Message message)
        {
            return Container.Resolve(ServiceType);
        }

        public Object 
        GetInstance(InstanceContext instanceContext)
        {
            return GetInstance(instanceContext,null);
        }

        public void 
        ReleaseInstance(InstanceContext instanceContext,Object instance)
        {
        }
    }
}
