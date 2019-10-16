//  ##########################################################################
//  # File Name: IModule.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides a mechanism for defining dependency injection bindings.
    /// </summary>
    ///  
    public
    interface IModule
    {
        string Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes the module by defining a set of dependency injection
        /// bindings for use in <c>IContainer</c> instances.
        /// </summary>
        /// 
        void
        Initialize();
    }
}

//  ##########################################################################
