//  ##########################################################################
//  # File Name: ReplyReplicator.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Common.Utility;
using Strata.Integration.Replier;
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
    class ReplyReplicator:
        AbstractEntityReplicator<String,Reply>
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        ReplyReplicator() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override Reply 
        MakeCopy(Reply original)
        {
            return original.Copy();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected override bool 
        HasUnassignedKey(Reply entity)
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
        AssignKey(Reply entity,String key)
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
