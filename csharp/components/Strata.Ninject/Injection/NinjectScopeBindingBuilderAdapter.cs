//  ##########################################################################
//  # File Name: NinjectScopeBindingBuilderAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Syntax;
using Ninject.Web.Common;
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
    class NinjectScopeBindingBuilderAdapter<T>:
        IScopeBindingBuilder<T>
    {
        private readonly IBindingWhenInNamedWithOrOnSyntax<T> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectScopeBindingBuilderAdapter(
            IBindingWhenInNamedWithOrOnSyntax<T> adaptee)
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

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T>
        WithScope<S>()
        {
            return
                new NinjectKeyBindingBuilderAdapter<T>(SelectScope<S>());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T>
        WithScope(Type scope)
        {
            return
                new NinjectKeyBindingBuilderAdapter<T>(SelectScope(scope));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IBindingNamedWithOrOnSyntax<T>
        SelectScope(Type scope)
        {
            if (scope == typeof(ThreadScope))
                return itsAdaptee.InThreadScope();

            if (scope == typeof(RequestScope))
                return itsAdaptee.InRequestScope();

            if (scope == typeof(SingletonScope))
                return itsAdaptee.InSingletonScope();

            if (scope == typeof(NullScope))
                return itsAdaptee.InTransientScope();

            throw new ArgumentOutOfRangeException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IBindingNamedWithOrOnSyntax<T>
        SelectScope<S>()
        {
            return SelectScope(typeof(S));
        }
    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectScopeBindingBuilderAdapter<U,T>:
        IScopeBindingBuilder<T>
        where U: T
    {
        private readonly IBindingWhenInNamedWithOrOnSyntax<U> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectScopeBindingBuilderAdapter(
            IBindingWhenInNamedWithOrOnSyntax<U> adaptee)
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

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T>
        WithScope<S>()
        {
            return
                new NinjectKeyBindingBuilderAdapter<U,T>(SelectScope<S>());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public IKeyBindingBuilder<T>
        WithScope(Type scope)
        {
            return
                new NinjectKeyBindingBuilderAdapter<U,T>(SelectScope(scope));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IBindingNamedWithOrOnSyntax<U>
        SelectScope(Type scope)
        {
            if (scope == typeof(ThreadScope))
                return itsAdaptee.InThreadScope();

            if (scope == typeof(RequestScope))
                return itsAdaptee.InRequestScope();

            if (scope == typeof(SingletonScope))
                return itsAdaptee.InSingletonScope();

            if (scope == typeof(NullScope))
                return itsAdaptee.InTransientScope();

            throw new ArgumentOutOfRangeException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private IBindingNamedWithOrOnSyntax<U>
        SelectScope<S>()
        {
            return SelectScope(typeof(S));
        }

    }
}

//  ##########################################################################
