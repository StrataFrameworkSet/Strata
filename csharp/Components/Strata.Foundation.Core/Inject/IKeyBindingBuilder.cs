//  ##########################################################################
//  # File Name: IKeyBindingBuilder.cs
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
    interface IKeyBindingBuilder<T>:
        IBindingBuilder<T>
    {
        IBindingBuilder<T>
        WithKey(string key);

        IBindingBuilder<T>
        WithKey<K>(K key)
            where K: Attribute;
    }
}

//  ##########################################################################
