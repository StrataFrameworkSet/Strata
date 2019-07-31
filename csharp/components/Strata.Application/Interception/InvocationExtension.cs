//  ##########################################################################
//  # File Name: InvocationExtension.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Extensions.Interception;
using Strata.Application.Messaging;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Logging;
using Strata.Foundation.Transfer;
using System;

namespace Strata.Application.Interception
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
            foreach (object argument in invocation.Request.Arguments)
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
            Type returnType = invocation.Request.Method.ReturnType;

            return (ServiceReply)Activator.CreateInstance(returnType); 
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static IUnitOfWorkProvider
        GetProvider(this IInvocation invocation)
        {
            if (invocation.Request.Target is IUnitOfWorkPropertySupplier)
                return
                    ((IUnitOfWorkPropertySupplier)invocation.Request.Target)
                        .Provider;

            throw
                new InvalidOperationException(
                    "Must implement IUnitOfWorkPropertySupplier");
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static IActionQueue
        GetQueue(this IInvocation invocation)
        {
            if (invocation.Request.Target is IUnitOfWorkPropertySupplier)
                return
                    ((IUnitOfWorkPropertySupplier)invocation.Request.Target)
                        .Queue;

            throw
                new InvalidOperationException(
                    "Must implement IUnitOfWorkPropertySupplier");
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static ILogger
        GetLogger(this IInvocation invocation)
        {
            if (invocation.Request.Target is IUnitOfWorkPropertySupplier)
                return
                    ((IUnitOfWorkPropertySupplier)invocation.Request.Target)
                        .Logger;

            throw
                new InvalidOperationException(
                    "Must implement IUnitOfWorkPropertySupplier");
        }

    }
}

//  ##########################################################################
