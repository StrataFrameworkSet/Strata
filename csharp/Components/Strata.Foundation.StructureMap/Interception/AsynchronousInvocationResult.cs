//  ##########################################################################
//  # File Name: SynchronousInvocationResult.cs
//  ##########################################################################

using System;
using System.Threading.Tasks;
using Strata.Foundation.Core.Interception;
using StructureMap.DynamicInterception;

namespace Strata.Foundation.StructureMap.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class AsynchronousInvocationResult:
        IInvocationResult
    {
        public Task<IMethodInvocationResult> Adaptee { get; protected set; }

        public
        AsynchronousInvocationResult(Task<IMethodInvocationResult> adaptee)
        {
            Adaptee = adaptee;
        }

        public object 
        GetResult()
        {
            return 
                Adaptee
                    .Result
                    .GetReturnValueOrThrow();
        }

        public T 
        GetResultAsType<T>()
        {
            if (GetResult() is T)
                return (T)GetResult();

            return default(T);
        }

        public bool 
        HasResultOfType<T>()
        {
            return GetResult() is T;
        }
    }
}

//  ##########################################################################
