//  ##########################################################################
//  # File Name: IScopeBindingBuilder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IScopeBindingBuilder<T>:
        IKeyBindingBuilder<T>
    {
        IKeyBindingBuilder<T>
        WithScope<S>();

        IKeyBindingBuilder<T>
        WithScope(Type scope);

    }
}

//  ##########################################################################
