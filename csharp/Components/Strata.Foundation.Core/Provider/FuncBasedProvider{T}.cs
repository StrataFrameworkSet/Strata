//  ##########################################################################
//  # File Name: FuncBasedProvider{T}.cs
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Provider
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
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
