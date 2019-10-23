//  ##########################################################################
//  # File Name: IUnitOfWorkProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Domain.Core.UnitOfWork
{
    public delegate K    KeyRetriever<K,T>(T entity);
    public delegate void KeyAssigner<K,T>(T entity,K key);

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Root interface of all repository contexts. Provides context-level
    /// locking and access to the current unit of work.
    /// </summary>
    ///  
    public
    interface IUnitOfWorkProvider
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the current unit of work for this provider. 
        /// </summary>
        /// 
        /// <returns>current unit of work</returns>
        /// 
        IUnitOfWork
        GetUnitOfWork();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Clears the current unit of work for this provider. 
        /// </summary>
        /// 
        void
        ClearUnitOfWork();
   }
}

//  ##########################################################################
