//  ##########################################################################
//  # File Name: IModuleAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Adapts module types from different frameworks to work in
    /// <c>Strata.Foundation.Core.Inject</c>.
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
