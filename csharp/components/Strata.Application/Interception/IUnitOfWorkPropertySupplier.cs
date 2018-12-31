//  ##########################################################################
//  # File Name: IUnitOfWorkPropertySupplier.cs
//  ##########################################################################

using Strata.Application.Messaging;
using Strata.Domain.UnitOfWork;
using Strata.Foundation.Logging;

namespace Strata.Application.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// This interface must be implemented by types that are decorated
    /// by the <c>UnitOfWorkInterceptor</c>.
    /// </summary>
    ///  
    public
    interface IUnitOfWorkPropertySupplier
    {
        IUnitOfWorkProvider Provider { get; }
        IActionQueue        Queue { get; }
        ILogger             Logger { get; }
    }
}

//  ##########################################################################
