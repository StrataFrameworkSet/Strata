//  ##########################################################################
//  # File Name: AbstractService.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Logging;
using Strata.Domain.UnitOfWork;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractService<S,E,O>:
        AbstractServiceEventSource<S,E,O>
        where S: IServiceEventSource<S,E,O>
        where E: IServiceEvent<S>
        where O: IServiceEventObserver<E>
    {
        protected IUnitOfWorkProvider Provider { get; set; }
        protected ILogger Logger { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractService(
            IUnitOfWorkProvider provider,
            ILogger             logger)
        {
            Provider = provider;
            Logger = logger;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected void
        PushRollbackAction(Action rollbackAction)
        {
            Provider
                .GetUnitOfWork()
                .PushRollbackAction(rollbackAction);
        }
    }
}

//  ##########################################################################
