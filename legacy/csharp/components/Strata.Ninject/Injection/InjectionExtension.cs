//  ##########################################################################
//  # File Name: InjectionExtension.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Syntax;

namespace Strata.Ninject.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public static
    class InjectionExtension
    {
        public static IBindingNamedWithOrOnSyntax<T>
        InDefaultScope<T>(this IBindingWhenInNamedWithOrOnSyntax<T> binder)
        {
            return AbstractModule.SetDefaultScope(binder);
        }
    }
}

//  ##########################################################################
