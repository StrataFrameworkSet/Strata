//  ##########################################################################
//  # File Name: SynchronousInvocationResult.cs
//  ##########################################################################

using System;
using Strata.Foundation.Core.Interception;
using StructureMap.DynamicInterception;

namespace Strata.Foundation.StructureMap.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class SynchronousInvocationResult:
        IInvocationResult
    {
        public IMethodInvocationResult Adaptee { get; protected set; }

        public 
        SynchronousInvocationResult(IMethodInvocationResult adaptee)
        {
            Adaptee = adaptee;
        }

        public object 
        GetResult()
        {
            return Adaptee.GetReturnValueOrThrow();
        }
        
        public T GetResultAsType<T>()
        {
            throw new NotImplementedException();
        }

        public bool HasResultOfType<T>()
        {
            throw new NotImplementedException();
        }
    }
}

//  ##########################################################################
