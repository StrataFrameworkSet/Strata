//  ##########################################################################
//  # File Name: UnitOfWorkInterceptor.cs
//  ##########################################################################

using Ninject.Extensions.Interception;
using Strata.Application.Messaging;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Logging;
using Strata.Foundation.Transfer;
using Strata.Ninject.Interception;
using System;
using Action = System.Action;

namespace Strata.Application.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Inceptor to wrap target method calls in a unit-of-work (transaction).
    /// </summary>
    ///  
    public
    class UnitOfWorkInterceptor:
        Interceptor
    {
        protected int   MaxRetries { get; set; }
        private ILogger Logger { get; set; }

        private static readonly DateTime theirEpoch =
            new DateTime(1970,1,1,0,0,0,DateTimeKind.Utc);

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        public
        UnitOfWorkInterceptor():
            this(1) {}

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        public
        UnitOfWorkInterceptor(int maxRetries)
        {
            MaxRetries  = maxRetries;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override void 
        Intercept(IInvocation invocation)
        {
            ServiceRequest request = null;

            try
            {
                IUnitOfWorkProvider provider = invocation.GetProvider();

                Logger  = invocation.GetLogger();
                request = invocation.GetRequest<ServiceRequest>();

                if (request == null)
                    throw
                        new ArgumentException(
                            "No ServiceRequest argument was found");

                request.ReceivedTimestamp = GetCurrentTimeInMilliseconds();
                LogRequest(request);
                DoBefore(invocation);

                for (int attempt = 1;attempt <= MaxRetries;++attempt)
                {
                    IUnitOfWork  unitOfWork = provider.GetUnitOfWork();
                    IActionQueue queue      = invocation.GetQueue();

                    if (queue != null)
                        queue.Clear();

                    try
                    {
                        ServiceReply reply = null;

                        DoProceed(invocation);
                        Commit(unitOfWork,request);
                        InvokeActions(invocation);
                        reply = invocation.GetReply<ServiceReply>();

                        try
                        {
                            LogReply(SetReplyProperties(reply,request));
                            return;
                        }
                        catch (Exception e)
                        {
                            LogException(request,e);
                            invocation.SetReply(
                                LogReply(
                                    SetReplyProperties(reply,request,e)));
                            return;
                        }
                    }
                    catch (OptimisticLockException e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);

                        if (attempt == MaxRetries)
                        {
                            invocation.SetReply(
                                LogReply(
                                    SetReplyProperties(
                                        invocation.CreateReply(),
                                        request,
                                        e)));
                            return;
                        }
                    }
                    catch (Exception e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);
                        invocation.SetReply(
                            LogReply(
                                SetReplyProperties(
                                    invocation.CreateReply(),
                                    request,
                                    e)));
                        return;
                    }
                }
            }
            catch (Exception e)
            {
                LogException(request,e);
                invocation.SetReply(
                    LogReply(
                        SetReplyProperties(invocation.CreateReply(),request,e)));
            }
            finally
            {
                DoAfter(invocation);
            }
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected virtual void
        InvokeActions(IInvocation invocation)
        {
            IActionQueue queue = invocation.GetQueue();

            if (queue == null)
                return;

            while (!queue.IsEmpty())
            {
                Action action = queue.Remove();

                if (action != null)
                    action.Invoke();
            }
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected virtual IUnitOfWorkProvider
        GetProvider(IInvocation invocation)
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
        protected virtual IActionQueue
        GetQueue(IInvocation invocation)
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
        protected virtual void
        Commit(IUnitOfWork unitOfWork,ServiceRequest request)
        {
            Logger.Info(
                string.Format(
                    "Committing {0}: {1}...",
                    request.GetType().Name,
                    request.CorrelationId));

            unitOfWork.Commit();

            Logger.Info(
                string.Format(
                    "Committed {0}: {1}.",
                    request.GetType().Name,
                    request.CorrelationId));
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected virtual void
        Rollback(IUnitOfWork unitOfWork,ServiceRequest request)
        {
            Logger.Info(
                string.Format(
                    "Rolling back {0}: {1}...",
                    request.GetType().Name,
                    request.CorrelationId));

            unitOfWork.Rollback();

            Logger.Info(
                string.Format(
                    "Rolled back {0}: {1}.",
                    request.GetType().Name,
                    request.CorrelationId));
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private ServiceReply
        SetReplyProperties(ServiceReply reply,ServiceRequest request)
        {
            reply.CorrelationId = request.CorrelationId;
            reply.ExecutionMilliseconds =
                GetCurrentTimeInMilliseconds() -
                request.ReceivedTimestamp;
            reply.SentTimestamp = GetCurrentTimeInMilliseconds();
            return reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private ServiceReply
        SetReplyProperties(ServiceReply reply,ServiceRequest request,Exception e)
        {
            reply = SetReplyProperties(reply,request);
            reply.IsSuccessful = false;
            reply.ExceptionType = e.GetType().FullName;
            reply.FailureMessage = e.Message;
            reply.StackTrace = e.StackTrace;
            return reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private ServiceRequest
        LogRequest(ServiceRequest request)
        {
            Logger.Info(
                string.Format(
                    "Receiving {0}: {1}...",
                    request.GetType().Name,
                    request.CorrelationId));

            return request;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private ServiceReply
        LogReply(ServiceReply reply)
        {
            Logger.Info(
                string.Format(
                    "Sending {0}: {1}...",
                    reply.GetType().Name,
                    reply.CorrelationId));

            return reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private void
        LogException(ServiceRequest request,Exception e)
        {
            Logger.Info(
                string.Format(
                    "Caught exception while processing {0}: {1}\n" +
                    "ExceptionType: {2}\n" +
                    "Message: {3}\n" +
                    "Stack Trace: {4}",
                    request.GetType().Name,
                    request.CorrelationId,
                    e.GetType().Name,
                    e.Message,
                    e.StackTrace));
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private long
        GetCurrentTimeInMilliseconds()
        {
            return (long)(DateTime.UtcNow - theirEpoch).TotalMilliseconds;
        }

    }
}

//  ##########################################################################
