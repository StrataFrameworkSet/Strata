//  ##########################################################################
//  # File Name: AbstractService.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Logging;
using Strata.Domain.UnitOfWork;
using System;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractService
    {
        protected IUnitOfWorkProvider Provider { get; set; }
        protected ILogger             Logger { get; set; }

        private static readonly DateTime theirEpoch = 
            new DateTime(1970,1,1,0,0,0,DateTimeKind.Utc);

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of <code>AbstractService</code>.
        /// </summary>
        ///  
        protected
        AbstractService(IUnitOfWorkProvider provider,ILogger logger)
        {
            Provider = provider;
            Logger   = logger;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected TReply
        Execute<TReply,TRequest>(
            Func<TRequest,TReply> action,
            TRequest              request,
            int                   maxRetries)
            where TRequest: ServiceRequest
            where TReply: ServiceReply, new()
        {
            IUnitOfWork unitOfWork = null;

            try
            {
                request.ReceivedTimestamp = GetCurrentTimeInMilliseconds();

                for (int attempt = 1;attempt <= maxRetries;++attempt)
                {
                    unitOfWork = Provider.GetUnitOfWork();

                    try
                    {
                        TReply reply = action(LogRequest(request));

                        Commit(unitOfWork,request);

                        try
                        {
                            return LogReply(SetReplyProperties(reply,request));
                        }
                        catch (Exception e)
                        {
                            LogException(request,e);
                            return LogReply(SetReplyProperties(reply,request,e));
                        }
                    }
                    catch (OptimisticLockException e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);

                        if (attempt == maxRetries)
                            return 
                                LogReply(
                                    SetReplyProperties(new TReply(),request,e));
                    }
                    catch (Exception e)
                    {
                        LogException(request,e);
                        Rollback(unitOfWork,request);
                        return 
                            LogReply(
                                SetReplyProperties(new TReply(),request,e));
                    }
                }
            }
            catch (Exception e)
            {
                LogException(request,e);
                return LogReply(SetReplyProperties(new TReply(),request,e));
            }

            throw 
                new InvalidOperationException("Impossible to reach this point.");
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected TReply
        Execute<TReply, TRequest>(Func<TRequest,TReply> action,TRequest request)
            where TRequest : ServiceRequest
            where TReply : ServiceReply, new()
        {
            return Execute<TReply,TRequest>(action,request,1);
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private void 
        Commit<TRequest>(IUnitOfWork unitOfWork,TRequest request)
            where TRequest: ServiceRequest
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
        Rollback<TRequest>(IUnitOfWork unitOfWork,TRequest request) 
            where TRequest : ServiceRequest
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
        private TReply
        SetReplyProperties<TReply,TRequest>(TReply reply,TRequest request)
            where TReply: ServiceReply
            where TRequest: ServiceRequest
        {
            reply.CorrelationId = request.CorrelationId;
            reply.ExecutionMilliseconds =
                GetCurrentTimeInMilliseconds() -
                request.ReceivedTimestamp;
            reply.SentTimestamp = GetCurrentTimeInMilliseconds();
            reply.IsSuccessful = true;
            return reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private TReply
        SetReplyProperties<TReply,TRequest>(
            TReply    reply,
            TRequest  request,
            Exception e)
            where TReply: ServiceReply
            where TRequest: ServiceRequest
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
        private TRequest
        LogRequest<TRequest>(TRequest request)
            where TRequest : ServiceRequest
        {
            Logger.Info("Receiving request: " + request.CorrelationId);
            return request;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private TReply
        LogReply<TReply>(TReply reply)
            where TReply: ServiceReply
        {
            Logger.Info( "Sending reply: " + reply.CorrelationId);
            return reply;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private void
        LogException<TRequest>(TRequest request,Exception e)
            where TRequest : ServiceRequest
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
