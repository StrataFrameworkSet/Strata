//  ##########################################################################
//  # File Name: RequestRepository.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Persistence.Repository;

namespace Strata.Integration.Requester
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class RequestRepository:
        AbstractRepository<String,IRequest>,
        IRequestRepository
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public 
        RequestRepository(IRepositoryProvider<String,IRequest> provider):
            base(provider) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public IRequest 
        InsertOrUpdateRequest(IRequest request)
        {
            return 
                !HasRequest( request.CorrelationId )
                    ? Provider.InsertEntity( request )
                    : Provider.UpdateEntity( request );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public void 
        RemoveRequest(String id)
        {
            IRequest request = GetRequest( id );

            if ( request != null )
                Provider.RemoveEntity( request );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public IRequest 
        GetRequest(String id)
        {
            return Provider.GetEntityByKey( id );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public bool 
        HasRequest(String id)
        {
            return Provider.HasEntityWithKey( id );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static String
        GetKey(IRequest request)
        {
            return request.CorrelationId;
        }

    }
}

//  ##########################################################################
