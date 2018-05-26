//  ##########################################################################
//  # File Name: IModuleAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Adapts module types from different frameworks to work in
    /// <c>Strata.Foundation.Injection</c>.
    /// </summary>
    ///  
    public
    interface IModuleAdapter
    {
        void
        SetAdaptee(IModule adaptee);

        ISourceBindingBuilder<T>
        Bind<T>();
    }
}

//  ##########################################################################
