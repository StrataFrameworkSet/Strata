//  ##########################################################################
//  # File Name: ITradeRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System;
using Strata.Persistence.Repository;
using System.Linq.Expressions;

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
        IRepository<long,Trade>
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
        GetTrades(Expression<Func<Trade,bool>> predicate);

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
        HasTrades(Expression<Func<Trade,bool>> predicate);
    }
}

//  ##########################################################################
