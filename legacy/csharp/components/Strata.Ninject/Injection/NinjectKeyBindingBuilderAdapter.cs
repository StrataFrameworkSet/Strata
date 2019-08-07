//  ##########################################################################
//  # File Name: NinjectKeyBindingBuilderAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Syntax;
using Strata.Foundation.Injection;
using System;

namespace Strata.Ninject.Injection
{

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectKeyBindingBuilderAdapter<T>:
        IKeyBindingBuilder<T>
    {
        private readonly IBindingNamedWithOrOnSyntax<T> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectKeyBindingBuilderAdapter(
            IBindingNamedWithOrOnSyntax<T> adaptee)
        {
            itsAdaptee = adaptee;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBinding<T> 
        GetBinding()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T> 
        WithKey(string key)
        {
            return
                new NinjectBindingBuilderAdapter<T>(itsAdaptee.Named(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T> 
        WithKey<K>(K key) where K : Attribute
        {
            return
                new NinjectBindingBuilderAdapter<T>(
                    itsAdaptee.Named(key.ToString()));
        }

    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectKeyBindingBuilderAdapter<U,T>:
        IKeyBindingBuilder<T>
        where U : T
    {
        private readonly IBindingNamedWithOrOnSyntax<U> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectKeyBindingBuilderAdapter(
            IBindingNamedWithOrOnSyntax<U> adaptee)
        {
            itsAdaptee = adaptee;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBinding<T>
        GetBinding()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T>
        WithKey(string key)
        {
            return
                new NinjectBindingBuilderAdapter<U,T>(itsAdaptee.Named(key));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T>
        WithKey<K>(K key) where K : Attribute
        {
            return
                new NinjectBindingBuilderAdapter<U,T>(
                    itsAdaptee.Named(key.ToString()));
        }

    }

}

//  ##########################################################################
