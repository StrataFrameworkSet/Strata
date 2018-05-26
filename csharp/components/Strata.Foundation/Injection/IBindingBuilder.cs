//  ##########################################################################
//  # File Name: IBindingBuilder.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IBindingBuilder<T>
    {
        IBinding<T>
        GetBinding();
    }
}

//  ##########################################################################
