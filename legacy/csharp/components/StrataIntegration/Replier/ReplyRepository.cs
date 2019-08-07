//  ##########################################################################
//  # File Name: ReplyRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Persistence.Repository;
using System;

namespace Strata.Integration.Replier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public  
    class ReplyRepository:
        AbstractRepository<String,Reply>,
        IReplyRepository
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>ReplyRepository</c> instance.
        /// </summary>
        /// 
        public 
        ReplyRepository(IRepositoryProvider<String,Reply> provider):
            base(provider) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public IReply 
        InsertOrUpdateReply(IReply reply)
        {
            return 
                !HasReply( reply.CorrelationId ) 
                    ? Provider.InsertEntity( (Reply)reply ) 
                    : Provider.UpdateEntity( (Reply)reply );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public void 
        RemoveReply(String id)
        {
            Reply reply = Provider.GetEntityByKey( id );

            if ( reply != null )
                Provider.RemoveEntity( reply );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public IReply 
        GetReply(String id)
        {
            return Provider.GetEntityByKey( id );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public bool 
        HasReply(String id)
        {
            return Provider.HasEntityWithKey( id );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public static String
        GetKey(IReply reply)
        {
            return reply.CorrelationId;
        }
    }
}

//  ##########################################################################
