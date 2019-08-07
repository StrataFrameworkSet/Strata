//  ##########################################################################
//  # File Name: IRepositoryContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Root interface of all repository contexts. Provides context-level
    /// locking and access to the current unit of work.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IRepositoryContext
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Acquires a read lock on the context. 
        /// </summary>
        /// 
        void
        LockForReading();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  Releases a read lock on the context.
        /// </summary>
        /// 
        void
        UnlockFromReading();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Acquires a write lock on the context.
        /// </summary>
        /// 
        void
        LockForWriting();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Releases a write lock on the context. 
        /// </summary>
        /// 
        void
        UnlockFromWriting();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the current unit of work for this context. 
        /// </summary>
        /// 
        /// <returns>current unit of work</returns>
        /// 
        IUnitOfWork
        GetUnitOfWork();
   }
}

//  ##########################################################################
