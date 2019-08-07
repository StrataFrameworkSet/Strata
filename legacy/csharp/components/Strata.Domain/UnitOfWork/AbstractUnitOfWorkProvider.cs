//  ##########################################################################
//  # File Name: AbstractUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Threading;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>IUnitOfWorkProvider</c> types.
    /// </summary>
    /// 
    public abstract
    class AbstractUnitOfWorkProvider:
        IUnitOfWorkProvider
    {

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractUnitOfWorkProvider</c> instance.
        /// </summary>
        /// 
        public 
        AbstractUnitOfWorkProvider()
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract IUnitOfWork
        GetUnitOfWork();

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void
        ClearUnitOfWork();

    }
}

//  ##########################################################################
