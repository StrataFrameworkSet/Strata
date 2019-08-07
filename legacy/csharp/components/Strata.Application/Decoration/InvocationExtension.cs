//  ##########################################################################
//  # File Name: InvocationExtension.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Castle.DynamicProxy;
using Strata.Foundation.Transfer;
using System;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public static
    class InvocationExtension
    {
        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static void
        SetReply<TReply>(this IInvocation invocation,TReply reply)
            where TReply : ServiceReply
        {
            invocation.ReturnValue = reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static TRequest
        GetRequest<TRequest>(this IInvocation invocation)
            where TRequest: ServiceRequest
        {
            foreach (object argument in invocation.Arguments)
                if (argument is TRequest)
                    return (TRequest)argument;

            return null;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static TReply
        GetReply<TReply>(this IInvocation invocation)
            where TReply : ServiceReply
        {
            if (invocation.ReturnValue is TReply)
                return (TReply)invocation.ReturnValue;

            return null;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static ServiceReply
        CreateReply(this IInvocation invocation)
        {
            Type returnType = invocation.Method.ReturnType;

            return (ServiceReply)Activator.CreateInstance(returnType); 
        }

    }
}

//  ##########################################################################
