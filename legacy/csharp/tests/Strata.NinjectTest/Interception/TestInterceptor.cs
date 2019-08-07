//  ##########################################################################
//  # File Name: TestInterceptor.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Extensions.Interception;
using System;

namespace Strata.Ninject.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class TestInterceptor:
        Interceptor
    {
  
        protected override void
        DoBefore(IInvocation invocation)
        {
            Console.Out.WriteLine("DoBefore: " + invocation.Request.Method.Name);
        }

        protected override void 
        DoAfter(IInvocation invocation)
        {
            Console.Out.WriteLine("DoAfter: " + invocation.Request.Method.Name);
        }

        protected override void 
        OnException(IInvocation invocation,Exception e)
        {
            Console.Out.WriteLine("OnException: " + invocation.Request.Method.Name + ": " + e.Message);
        }
    }
}

//  ##########################################################################
