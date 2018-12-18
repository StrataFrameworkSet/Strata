//  ##########################################################################
//  # File Name: AbstractModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Modules;
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
    public abstract
    class AbstractModule:
        NinjectModule
    {
        public static Type DefaultScope { get; set; }

        static
        AbstractModule()
        {
            DefaultScope = typeof(ThreadScope);
        }

        public static IBindingNamedWithOrOnSyntax<T>
        SetDefaultScope<T>(IBindingWhenInNamedWithOrOnSyntax<T> binder)
        {
            if (DefaultScope == typeof(RequestScope))
                return binder.InRequestScope();
            if (DefaultScope == typeof(ThreadScope))
                return binder.InThreadScope();
            if (DefaultScope == typeof(SingletonScope))
                return binder.InSingletonScope();
            if (DefaultScope == typeof(NullScope))
                return binder.InTransientScope();

            throw new InvalidOperationException("Unknown scope");
        }
    }
}

//  ##########################################################################
