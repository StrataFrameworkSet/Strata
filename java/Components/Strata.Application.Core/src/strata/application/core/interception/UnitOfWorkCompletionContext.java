//////////////////////////////////////////////////////////////////////////////
// UnitOfWorkCompletionContext.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import org.aopalliance.intercept.MethodInvocation;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.foundation.core.transfer.ExceptionData;
import strata.foundation.core.transfer.ServiceReply;
import strata.foundation.core.transfer.ServiceRequest;
import strata.foundation.core.utility.CompletionContext;

import java.time.Instant;
import java.util.Optional;

public
class UnitOfWorkCompletionContext
    extends CompletionContext<Object>
{
    private MethodInvocation              itsInvocation;
    private Optional<IUnitOfWorkProvider> itsProvider;
    private Optional<IUnitOfWork>         itsUnitOfWork;
    private Optional<ServiceRequest>      itsRequest;
    private int                           itsAttempt;
    private int                           itsMaxAttempt;

    public
    UnitOfWorkCompletionContext(
        MethodInvocation    invocation,
        IUnitOfWorkProvider provider,
        int                 maxAttempts)
    {
        super(null,null);
        itsInvocation = invocation;
        itsProvider   = Optional.of(provider);
        itsUnitOfWork = Optional.empty();
        itsAttempt    = 0;
        itsMaxAttempt = maxAttempts;
    }

    public
    UnitOfWorkCompletionContext(
        MethodInvocation    invocation,
        IUnitOfWorkProvider provider)
    {
        this(invocation,provider,3);
    }

    @Override
    public UnitOfWorkCompletionContext
    setResult(Object result)
    {
        return (UnitOfWorkCompletionContext)super.setResult(result);
    }

    @Override
    public UnitOfWorkCompletionContext
    clearResult()
    {
        return (UnitOfWorkCompletionContext)super.clearResult();
    }

    @Override
    public UnitOfWorkCompletionContext
    setException(Throwable exception)
    {
        return (UnitOfWorkCompletionContext)super.setException(exception);
    }

    @Override
    public UnitOfWorkCompletionContext
    clearException()
    {
        return (UnitOfWorkCompletionContext)super.clearException();
    }

    public UnitOfWorkCompletionContext
    setProvider(IUnitOfWorkProvider providier)
    {
        itsProvider = Optional.of(providier);
        return this;
    }

    public UnitOfWorkCompletionContext
    setUnitOfWork(IUnitOfWork unitOfWork)
    {
        itsUnitOfWork = Optional.of(unitOfWork);
        return this;
    }

    public UnitOfWorkCompletionContext
    setRequest(ServiceRequest request)
    {
        itsRequest = Optional.ofNullable(request);
        return this;
    }

    @Override
    public Object
    getResultIfPresent()
    {
        Object result = super.getResultIfPresent();

        if (result instanceof ServiceReply)
        {
            ServiceReply reply = (ServiceReply)result;

            if (hasException() && !reply.hasException())
            {
                reply.setException(ExceptionData.of(getExceptionIfPresent()));

                if (reply.isSuccess())
                    reply.setSuccess(false);
            }
        }

        return result;
    }

    public MethodInvocation
    getInvocation()
    {
        return itsInvocation;
    }

    public Optional<IUnitOfWorkProvider>
    getProvider()
    {
        return itsProvider;
    }

    public IUnitOfWorkProvider
    getProviderIfPresent()
    {
        return hasProvider() ? itsProvider.get() : null;
    }

    public Optional<IUnitOfWork>
    getUnitOfWork()
    {
        return itsUnitOfWork;
    }

    public IUnitOfWork
    getUnitOfWorkIfPresent()
    {
        return hasUnitOfWork() ? itsUnitOfWork.get() : null;
    }

    public Optional<ServiceRequest>
    getRequest()
    {
        return itsRequest;
    }

    public ServiceRequest
    getRequestIfPresent()
    {
        return hasRequest() ? itsRequest.get() : null;
    }

    public int
    getAttempt() { return itsAttempt; }

    public int
    getMaxAttempt() { return itsMaxAttempt; }

    public boolean
    hasProvider()
    {
        return itsProvider.isPresent();
    }

    public boolean
    hasUnitOfWork()
    {
        return itsUnitOfWork.isPresent();
    }

    public boolean
    hasRequest()
    {
        return itsRequest.isPresent();
    }

    public boolean
    hasMoreAttempts()
    {
        return itsAttempt < itsMaxAttempt;
    }

    public boolean
    isLastAttempt()
    {
        return itsAttempt == (itsMaxAttempt - 1);
    }

    @Override
    public UnitOfWorkCompletionContext
    start()
    {
        return (UnitOfWorkCompletionContext)super.start();
    }

    @Override
    public UnitOfWorkCompletionContext
    stop()
    {
        return (UnitOfWorkCompletionContext)super.stop();
    }

    public UnitOfWorkCompletionContext
    incrementAttempt()
    {
        ++itsAttempt;
        return this;
    }

    public UnitOfWorkCompletionContext
    initializeReply()
    {
        ServiceRequest request = getRequestIfPresent();
        Object         result  = super.getResultIfPresent();

        if (request != null && result instanceof ServiceReply)
        {
            ServiceReply reply = (ServiceReply)result;

            reply
                .setCorrelationId(request.getCorrelationId())
                .setTimestamp(Instant.now());
        }

        return this;
    }
}

//////////////////////////////////////////////////////////////////////////////
