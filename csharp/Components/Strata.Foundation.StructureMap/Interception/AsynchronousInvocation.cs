//  ##########################################################################
//  # File Name: SynchronousInvocation.cs
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
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
    class AsynchronousInvocation:
        IInvocation
    {
        public object                 
        Target { get { return Adaptee.TargetInstance; } }

        public MethodInfo             
        Method { get { return Adaptee.MethodInfo; } }

        public IList<object>          
        Arguments { get { return ToList(Adaptee.Arguments); } }

        public object 
        ReturnValue
        {
            get { return itsReturnValue; }
            set
            {
                itsReturnValue = value;
                Adaptee.CreateResult(itsReturnValue);
            }
        }

        public IAsyncMethodInvocation Adaptee { get; protected set; }

        private object itsReturnValue;

        public
        AsynchronousInvocation(IAsyncMethodInvocation adaptee)
        {
            Adaptee = adaptee;
        }

        public virtual IInvocationResult
        Proceed()
        {
            return
                new AsynchronousInvocationResult(Adaptee.InvokeNextAsync());
        }

        public virtual T
        GetArgumentAsType<T>(string name)
        {
            IArgument argument = Adaptee.GetArgument(name);

            if (argument != null && argument.Value is T)
                return
                    (T)argument.Value;

            return default(T);
        }

        public virtual bool
        HasArgumentOfType<T>(string name)
        {
            IArgument argument = Adaptee.GetArgument(name);

            return (argument != null && argument.Value is T);
        }

        private static IList<object>
        ToList(IList<IArgument> input)
        {
            IList<object> output = new List<object>();

            foreach (IArgument arg in input)
                output.Add(arg.Value);

            return output;
        }

    }
}

//  ##########################################################################
