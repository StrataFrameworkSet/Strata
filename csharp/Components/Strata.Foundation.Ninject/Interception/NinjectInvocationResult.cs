//  ##########################################################################
//  # File Name: NinjectInvocationResult.cs
//  ##########################################################################

using System.Threading.Tasks;
using Strata.Foundation.Core.Interception;

namespace Strata.Foundation.Ninject.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class NinjectInvocationResult :
        IInvocationResult
    {
        private readonly object itsResult;

        public 
        NinjectInvocationResult(object result)
        {
            itsResult = result;
        }

        public object 
        GetResult()
        {
            return itsResult;
        }

        public T 
        GetResultAsType<T>()
        {
            return
                HasResultOfType<T>()
                    ? (T)itsResult
                    : default(T);
        }

        public bool 
        HasResultOfType<T>()
        {
            return itsResult is T;
        }
    }
}

//  ##########################################################################
