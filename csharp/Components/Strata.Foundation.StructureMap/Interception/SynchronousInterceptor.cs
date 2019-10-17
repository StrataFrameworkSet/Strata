//  ##########################################################################
//  # File Name: SynchronousInterceptor.cs
//  ##########################################################################

using Strata.Foundation.Core.Interception;
using StructureMap.DynamicInterception;

namespace Strata.Foundation.StructureMap.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public 
    class SynchronousInterceptor<I>:
        ISyncInterceptionBehavior
        where I:IInterceptor, new()
    {
        public I TargetInterceptor { get; protected set; }

        public 
        SynchronousInterceptor()
        {
            TargetInterceptor = new I();
        }

        public IMethodInvocationResult 
        Intercept(ISyncMethodInvocation invocation)
        {
            return
                ((SynchronousInvocationResult)
                    TargetInterceptor
                        .Intercept(new SynchronousInvocation(invocation)))
                        .Adaptee;
        }
    }
}

//  ##########################################################################
