//  ##########################################################################
//  # File Name: IReplyRepository.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Persistence.Repository;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A repository for storing and retrieving <c>IReply</c> objects.
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IReplyRepository:
        IRepository
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an new reply or updates an existing reply in the 
        /// repository.
        /// </summary>
        /// 
        /// <param name="reply"></param>
        /// 
        IReply 
        InsertOrUpdateReply(IReply reply);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes a Reply from the repository.
        /// </summary>
        /// 
        /// <param name="id"></param>
        /// 
        void 
        RemoveReply(String id);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the Reply associated with the specified id, or returns null
        /// if it doesn't exist.
        /// </summary>
        ///
        /// <param name="id"></param>
        ///  
        IReply 
        GetReply(String id);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if the repository has a reply associated with
        /// the specified id, otherwise false.
        /// </summary>
        /// 
        /// <param name="id"></param>
        /// 
        bool 
        HasReply(String id);

    }
}

//  ##########################################################################
