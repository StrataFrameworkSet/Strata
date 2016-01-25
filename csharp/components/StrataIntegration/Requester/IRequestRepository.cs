//  ##########################################################################
//  # File Name: IRequestRepository.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Persistence.Repository;

namespace Strata.Integration.Requester
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IRequestRepository:
        IRepository
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Inserts an new request or updates an existing request in the 
        /// repository.
        /// </summary>
        /// 
        IRequest 
        InsertOrUpdateRequest(IRequest request);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes a request from the repository.
        /// </summary>
        /// 
        void 
        RemoveRequest(String id);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the request associated with the specified id, or returns null
        /// if it doesn't exist.
        /// </summary>
        /// 
        IRequest 
        GetRequest(String id);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns true if the repository has a request associated with
        /// the specified id, otherwise false.
        /// </summary>
        /// 
        bool 
        HasRequest(String id);

    }
}

//  ##########################################################################
