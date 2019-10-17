//  ##########################################################################
//  # File Name: AsynchronousInterceptor.cs
//  ##########################################################################

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
    class AsynchronousInterceptor<I>:
        IAsyncInterceptionBehavior
        where I:IInterceptor, new()
    {
        public I TargetInterceptor { get; protected set; }

        public
        AsynchronousInterceptor()
        {
            TargetInterceptor = new I();
        }

        public Task<IMethodInvocationResult>
        InterceptAsync(IAsyncMethodInvocation invocation)
        {
            return
                ((AsynchronousInvocationResult)
                    TargetInterceptor
                        .Intercept(new AsynchronousInvocation(invocation)))
                        .Adaptee;
        }
    }
}

//  ##########################################################################
