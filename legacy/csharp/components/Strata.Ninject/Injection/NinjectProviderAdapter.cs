//  ##########################################################################
//  # File Name: NinjectProviderAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Injection;
using System;
using SystemEx.Injection;

namespace Strata.Ninject.Injection
{
    using NinjectActivation = global::Ninject.Activation;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectProviderAdapter<T> :
        NinjectActivation.Provider<T>
    {
        private IProvider<T> itsAdaptee;

        public
        NinjectProviderAdapter(IProvider<T> adaptee)
        {
            itsAdaptee = adaptee;
        }

        protected override T 
        CreateInstance(NinjectActivation.IContext context)
        {
            return itsAdaptee.Get();
        }

    }
}

//  ##########################################################################
