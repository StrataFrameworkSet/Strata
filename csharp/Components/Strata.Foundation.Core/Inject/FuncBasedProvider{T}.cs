//  ##########################################################################
//  # File Name: FuncBasedProvider{T}.cs
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class FuncBasedProvider<T>:
        IProvider<T>
    {
        private readonly Func<T> itsImp;

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
