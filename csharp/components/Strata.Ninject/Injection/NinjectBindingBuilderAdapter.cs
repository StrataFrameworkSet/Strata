//  ##########################################################################
//  # File Name: NinjectBindingBuilderAdapter.cs
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
    class NinjectBindingBuilderAdapter<T>:
        IBindingBuilder<T>
    {
        private IBindingWithOrOnSyntax<T> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectBindingBuilderAdapter(IBindingWithOrOnSyntax<T> adaptee)
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
    }

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectBindingBuilderAdapter<U,T>:
        IBindingBuilder<T>
        where U: T
    {
        private IBindingWithOrOnSyntax<U> itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>/>
        /// 
        public
        NinjectBindingBuilderAdapter(IBindingWithOrOnSyntax<U> adaptee)
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
    }
}

//  ##########################################################################
