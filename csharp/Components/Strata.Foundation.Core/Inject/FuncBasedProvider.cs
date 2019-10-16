//  ##########################################################################
//  # File Name: FuncBasedProvider.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Core.Provider;
using System;

namespace Strata.Foundation.Core.Inject
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
