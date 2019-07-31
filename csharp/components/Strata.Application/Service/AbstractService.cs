//  ##########################################################################
//  # File Name: AbstractService.cs
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
    class AbstractService:
        IUnitOfWorkPropertySupplier
    {
        public IUnitOfWorkProvider Provider { get; set; }
        public IActionQueue        Queue { get; set; }
        public ILogger             Logger { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractService(
            IUnitOfWorkProvider provider,
            ILogger             logger):
            this(provider,null,logger) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractService(
            IUnitOfWorkProvider provider,
            IActionQueue        queue,
            ILogger             logger)
        {
            Provider = provider;
            Queue    = queue;
            Logger   = logger;
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
