//  ##########################################################################
//  # File Name: RequestReplicator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
using Strata.Integration.Replier;
using Strata.Integration.Requester;
using Strata.Persistence.InMemoryRepository;

namespace Strata.Integration.InMemoryReplier
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class RequestReplicator:
        AbstractEntityReplicator<String,IRequest>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        RequestReplicator() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override IRequest 
        MakeCopy(IRequest original)
        {
            return original.Copy();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override bool 
        HasUnassignedKey(IRequest entity)
        {
            return 
                String.IsNullOrEmpty(entity.CorrelationId);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This method should not be called.
        /// </summary>
        /// 
        protected override void 
        AssignKey(IRequest entity,String key)
        {
            throw new NotImplementedException("should not have been called");
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// This method should not be called.
        /// </summary>
        /// 
        protected override String 
        GenerateKey()
        {
            throw new NotImplementedException("should not have been called");            
        }
    }
}

//  ##########################################################################
