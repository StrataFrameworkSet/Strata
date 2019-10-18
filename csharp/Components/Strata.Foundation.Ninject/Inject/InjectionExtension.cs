//  ##########################################################################
//  # File Name: InjectionExtension.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Ninject.Syntax;
using Ninject.Web.Common;
using Strata.Foundation.Core.Inject;

namespace Strata.Foundation.Ninject.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public static
    class InjectionExtension
    {

        public static IBindingWhenInNamedWithOrOnSyntax<T> 
        ToProviderT<T>(
            this IBindingToSyntax<T> binder,
            IProvider<T>             provider)
        {
            return binder.ToProvider(new NinjectProvider<T>(provider));
        }

        public static IBindingNamedWithOrOnSyntax<T>
        InDefaultScope<T>(this IBindingWhenInNamedWithOrOnSyntax<T> binder)
        {
            if (AbstractModule.DefaultScope == Scope.REQUEST_SCOPE)
                return binder.InRequestScope();
            if (AbstractModule.DefaultScope == Scope.THREAD_SCOPE)
                return binder.InThreadScope();
            if (AbstractModule.DefaultScope == Scope.SINGLETON_SCOPE)
                return binder.InSingletonScope();
            if (AbstractModule.DefaultScope == Scope.NULL_SCOPE)
                return binder.InTransientScope();

            throw new InvalidOperationException("Unknown scope");
        }
    }
}

//  ##########################################################################
