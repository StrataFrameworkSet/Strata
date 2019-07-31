//  ##########################################################################
//  # File Name: IUnitOfWork.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an abstraction for grouping operations into a <a 
    /// href="http://tinyurl/fifx-arch/Patterns/UnitOfWork/UnitOfWork.html">
    /// unit-of-work</a> (i.e. a transaction).
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IUnitOfWork
    {
        IDictionary<String,String> ErrorList { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the specified repository method.
        /// </summary>
        /// 
        /// <param name="name">repository method name</param>
        /// <returns>repository method</returns>
        /// 
        IRepositoryMethod
        GetRepositoryMethod(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Commits the current unit of work to the underlying storage.
        /// </summary>
        /// 
        /// <precondition>
        /// this.IsActive() == true
        /// </precondition>
        /// <postcondition>
        /// this.IsActive() == false
        /// </postcondition>
        /// <postcondition>
        /// this.IsCommitted() == true
        /// </postcondition>
        /// 
        /// <exception cref="CommitFailedException">
        /// The unit of work failed to commit. 
        /// </exception>
        /// 
        void
        Commit();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Rolls back the current unit of work in the event of an error.
        /// </summary>
        /// 
        /// <postcondition>
        /// this.IsRolledBack() == true
        /// </postcondition>
        /// 
        /// <exception cref="RollbackFailedException">
        /// The unit of work failed to roll back. 
        /// </exception>
        /// 
        void
        Rollback();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is active.
        /// </summary>
        /// 
        /// <returns>true if active, false otherwise</returns>
        /// 
        bool 
        IsActive();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is committed.
        /// </summary>
        /// 
        /// <returns>true if committed, false otherwise</returns>
        /// 
        bool 
        IsCommitted();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Checks if the current unit of work is rolled back.
        /// </summary>
        /// 
        /// <returns>true if rolled back, false otherwise</returns>
        /// 
        bool 
        IsRolledBack();
    }
}

//  ##########################################################################
