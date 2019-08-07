//  ##########################################################################
//  # File Name: ITradeRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System;
using Strata.Persistence.Repository;

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
    interface ITradeRepository:
        IRepository
    {
        Trade
        InsertTrade(Trade trade);

        Trade
        UpdateTrade(Trade trade);

        void
        RemoveTrade(Trade trade);

        void
        RemoveTradesWithTradeIds(ISet<int> tradeIds);

        Trade
        GetTrade(long tradeKey);

        Trade
        GetTradeByTradeId(int tradeId);

        Trade
        GetTradeContainingAccountAllocation(int accountAllocId);

        IList<Trade> 
        GetTrades(Func<Trade,bool> predicate);

        IList<Trade> 
        GetTradesWithManagerId(int managerId);

        IList<Trade>
        GetTradesWithTradeId(int tradeId);

        IList<Trade> 
        GetAllTrades();

        bool
        HasTrade(long tradeKey);

        bool
        HasTradeWithTradeId(int tradeId);

        bool
        HasTradeWithCusip(string cusip);

        bool
        HasTrades(Func<Trade,bool> predicate);
    }
}

//  ##########################################################################
