//  ##########################################################################
//  # File Name: FuncBasedProvider.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using SystemEx.Injection;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class FuncBasedProvider<T>:
        IProvider<T>
    {
        private Func<T> itsImp;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        FuncBasedProvider(Func<T> imp)
        {
            itsImp = imp;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        Get()
        {
            return itsImp.Invoke();
        }
    }
}

//  ##########################################################################
