//  ##########################################################################
//  # File Name: AbstractProcessor{S,E,O}.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Logging;
using Strata.Domain.UnitOfWork;
using Strata.Application.Interception;
using Strata.Application.Messaging;

namespace Strata.Application.Service
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractProcessor<S,E,O>:
        AbstractServiceEventSource<S,E,O>
        where S: IServiceEventSource<S,E,O>
        where E: IServiceEvent<S>
        where O: IServiceEventObserver<E>
    {
        protected IUnitOfWorkProvider Provider { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractProcessor(IUnitOfWorkProvider provider)
        {
            Provider = provider;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected void
        PushRollbackAction(RollbackAction rollbackAction)
        {
            Provider
                .GetUnitOfWork()
                .PushRollbackAction(rollbackAction);
        }
    }
}

//  ##########################################################################
