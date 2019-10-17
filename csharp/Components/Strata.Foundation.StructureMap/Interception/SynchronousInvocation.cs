//  ##########################################################################
//  # File Name: SynchronousInvocation.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Reflection;
using Strata.Foundation.Core.Interception;
using StructureMap.DynamicInterception;

namespace Strata.Foundation.StructureMap.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class SynchronousInvocation:
        IInvocation
    {
        public object Target { get; }
        public MethodInfo Method { get; }
        public IList<object> Arguments { get; }
        public object ReturnValue { get; set; }

        public ISyncMethodInvocation Adaptee { get; protected set; }
        
        public
        SynchronousInvocation(ISyncMethodInvocation adaptee)
        {
            Adaptee = adaptee;
        }

        public IInvocationResult 
        Proceed()
        {
            return new SynchronousInvocationResult(Adaptee.InvokeNext());
        }

        public T 
        GetArgumentAsType<T>(string name)
        {
            return default;
        }

        public bool 
        HasArgumentOfType<T>(string name)
        {
            throw new NotImplementedException();
        }
    }
}

//  ##########################################################################
