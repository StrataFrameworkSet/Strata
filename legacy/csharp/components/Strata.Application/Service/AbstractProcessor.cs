//  ##########################################################################
//  # File Name: AbstractProcessor.cs
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
    class AbstractProcessor
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
