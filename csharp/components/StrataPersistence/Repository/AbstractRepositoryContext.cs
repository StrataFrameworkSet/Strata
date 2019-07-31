//  ##########################################################################
//  # File Name: AbstractRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Threading;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>IRepositoryContext</c> types.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractRepositoryContext:
        IRepositoryContext
    {
        private ReaderWriterLockSlim synchronizer;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractRepositoryContext</c> instance.
        /// </summary>
        /// 
        public 
        AbstractRepositoryContext()
        {
            synchronizer = 
                new ReaderWriterLockSlim(
                    LockRecursionPolicy.SupportsRecursion);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryContext.LockForReading()"/>
        /// </summary>
        /// 
        public void 
        LockForReading()
        {
            synchronizer.EnterReadLock();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryContext.UnlockFromReading()"/>
        /// </summary>
        /// 
        public void 
        UnlockFromReading()
        {
            synchronizer.ExitReadLock();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryContext.LockForWriting()"/>
        /// </summary>
        /// 
        public void 
        LockForWriting()
        {
            synchronizer.EnterWriteLock();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryContext.UnlockFromWriting()"/>
        /// </summary>
        /// 
        public void 
        UnlockFromWriting()
        {
            synchronizer.ExitWriteLock();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryContext.GetUnitOfWork()"/>
        /// </summary>
        /// 
        public abstract IUnitOfWork
        GetUnitOfWork();
    }
}

//  ##########################################################################
