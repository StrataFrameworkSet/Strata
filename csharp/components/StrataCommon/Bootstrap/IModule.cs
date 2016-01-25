//  ##########################################################################
//  # File Name: IServerModule.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Microsoft.Practices.Unity;

namespace Strata.Common.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
