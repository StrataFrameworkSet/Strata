//  ##########################################################################
//  # File Name: ISourceBindingBuilder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using SystemEx.Injection;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface ISourceBindingBuilder<T>:
        IScopeBindingBuilder<T>
    {
        IScopeBindingBuilder<T>
        ToType<S>()
            where S: T;

        IScopeBindingBuilder<T>
        ToProvider(IProvider<T> provider);

        IScopeBindingBuilder<T>
        ToProvider<P>()
            where P: IProvider<T>, new();

        IScopeBindingBuilder<T>
        ToInstance(T instance);
    }
}

//  ##########################################################################
