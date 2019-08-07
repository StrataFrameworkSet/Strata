//  ##########################################################################
//  # File Name: NinjectSourceBindingBuilderAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Syntax;
using Strata.Foundation.Injection;
using System;
using SystemEx.Injection;

namespace Strata.Ninject.Injection
{
    using INinjectBinding = global::Ninject.Planning.Bindings.IBinding;
    using NinjectBinding = global::Ninject.Planning.Bindings.Binding;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectSourceBindingBuilderAdapter<T>:
        ISourceBindingBuilder<T>
    {
        private IBindingToSyntax<T> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectSourceBindingBuilderAdapter(IBindingToSyntax<T> adaptee)
        {
            itsAdaptee = adaptee;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBinding<T> GetBinding()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IScopeBindingBuilder<T> 
        ToInstance(T instance)
        {
            return 
                new NinjectScopeBindingBuilderAdapter<T>(
                    itsAdaptee.ToConstant<T>(instance));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IScopeBindingBuilder<T> 
        ToProvider(IProvider<T> provider)
        {
            return 
                new NinjectScopeBindingBuilderAdapter<T>(
                    itsAdaptee.ToProvider(
                        new NinjectProviderAdapter<T>(provider)));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IScopeBindingBuilder<T> 
        ToProvider<P>()
            where P : IProvider<T>, new()
        {
            return
                new NinjectScopeBindingBuilderAdapter<T>(
                    itsAdaptee.ToProvider(
                        new NinjectProviderAdapter<T>(new P())));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IScopeBindingBuilder<T> 
        ToType<S>() where S: T
        {
            return
                new NinjectScopeBindingBuilderAdapter<S,T>(
                    itsAdaptee.To<S>());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T> 
        WithKey(string key)
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IBindingBuilder<T> 
        WithKey<K>(K key) where K : Attribute
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T>
        WithScope<S>()
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T> 
        WithScope(Type scope)
        {
            throw new NotImplementedException();
        }
    }
}

//  ##########################################################################
