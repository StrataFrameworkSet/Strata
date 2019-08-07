//  ##########################################################################
//  # File Name: TradeReplicator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Utility;
using Strata.Persistence.InMemoryRepository;

namespace Strata.Persistence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class TradeReplicator:
        AbstractEntityReplicator<long,Trade>
    {
        public 
        TradeReplicator() {}

        protected override Trade 
        MakeCopy(Trade original)
        {
            return original.Copy();
        }

        protected override bool 
        HasUnassignedKey(Trade entity)
        {
            return entity.TradeKey == 0;
        }

        protected override void 
        AssignKey(Trade entity,long key)
        {
            entity.TradeKey = key;
        }

        protected override long 
        GenerateKey()
        {
            Random generator = new Random();
            byte[] buffer    = new byte[8];

            generator.NextBytes(buffer);
            return BitConverter.ToInt64(buffer,0);
        }
    }
}

//  ##########################################################################
