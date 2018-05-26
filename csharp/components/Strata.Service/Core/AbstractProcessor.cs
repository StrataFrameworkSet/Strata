//  ##########################################################################
//  # File Name: AbstractProcessor.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Logging;
using Strata.Persistence.UnitOfWork;

namespace Strata.Service.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractProcessor
    {
        protected IUnitOfWorkProvider Provider { get; set; }
        protected ILogger             Logger { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        AbstractProcessor(
            IUnitOfWorkProvider provider,
            ILogger             logger)
        {
            Provider = provider;
            Logger   = logger;
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
