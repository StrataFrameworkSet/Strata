//  ##########################################################################
//  # File Name: IServerModule.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Microsoft.Practices.Unity;

namespace Strata.Common.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IModule
    {
        String Name { get; }

        void 
        Initialize(IUnityContainer container);
    }
}

//  ##########################################################################
