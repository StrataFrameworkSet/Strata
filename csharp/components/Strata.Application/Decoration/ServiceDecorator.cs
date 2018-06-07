//  ##########################################################################
//  # File Name: ServiceDecorator.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Castle.DynamicProxy;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Decoration;
using Strata.Foundation.Logging;
using Strata.Foundation.Transfer;
using System;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class ServiceDecorator:
        Decorator
    {
        protected IUnitOfWorkProvider Provider { get; set; }
        protected ILogger             Logger { get; set; }
        protected int                 MaxRetries { get; set; }

        private static readonly DateTime theirEpoch =
            new DateTime(1970,1,1,0,0,0,DateTimeKind.Utc);

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        public
        ServiceDecorator():
            this(null,null,0)
        {
            throw 
                new InvalidOperationException(
                    "This constructor is not meant to be used. " +
                    "It is only provided to satisfy generic type constraints.");
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        public
        ServiceDecorator(IUnitOfWorkProvider provider,ILogger logger):
            this(provider,logger,1) {}

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        public
        ServiceDecorator(
            IUnitOfWorkProvider provider,
            ILogger             logger,
            int                 maxRetries)
        {
            Provider   = provider;
            Logger     = logger;
            MaxRetries = maxRetries;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override void 
        Intercept(IInvocation invocation)
        {
            ServiceRequest request = null;
            IUnitOfWork    unitOfWork = null;

            try
            {
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
                    unitOfWork = Provider.GetUnitOfWork();

                    try
                    {
                        ServiceReply reply = null;

                        DoProceed(invocation);
                        Commit(unitOfWork,request);
                        reply = invocation.GetReply<ServiceReply>();

                        try
                        {
                            LogReply(SetReplyProperties(reply,request));
                        }
                        catch (Exception e)
                        {
                            LogException(request,e);
                            invocation.SetReply(
                                LogReply(
                                    SetReplyProperties(reply,request,e)));
                        }
                    }
                    catch (OptimisticLockException e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);

                        if (attempt == MaxRetries)
                            invocation.SetReply(
                                LogReply(
                                    SetReplyProperties(
                                        invocation.CreateReply(),
                                        request,
                                        e)));
                    }
                    catch (Exception e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);
                        invocation.SetReply(
                            LogReply(
                                SetReplyProperties(invocation.CreateReply(),request,e)));
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
        private void
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
        private void
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
            Logger.Info("Receiving request: " + request.CorrelationId);
            return request;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private ServiceReply
        LogReply(ServiceReply reply)
        {
            Logger.Info("Sending reply: " + reply.CorrelationId);
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
                    "Caught exception while processing request: {0}\n" +
                    "Message: {1}\n" +
                    "Stack Trace: {2}",
                    request.CorrelationId,
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
