//  ##########################################################################
//  # File Name: TestDecorator.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Castle.DynamicProxy;
using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class TestDecorator:
        Decorator
    {
  
        protected override void
        DoBefore(IInvocation invocation)
        {
            Console.Out.WriteLine("DoBefore: " + invocation.Method.Name);
        }

        protected override void 
        DoAfter(IInvocation invocation)
        {
            Console.Out.WriteLine("DoAfter: " + invocation.Method.Name);
        }

        protected override void 
        OnException(IInvocation invocation,Exception e)
        {
            Console.Out.WriteLine("OnException: " + invocation.Method.Name + ": " + e.Message);
        }
    }
}

//  ##########################################################################
