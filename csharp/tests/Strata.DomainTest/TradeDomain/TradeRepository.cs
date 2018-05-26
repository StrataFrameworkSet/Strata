//  ##########################################################################
//  # File Name: TradeRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using Strata.Domain.NamedQuery;
using Strata.Domain.Repository;
using Strata.Domain.UnitOfWork;

namespace Strata.Domain.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class TradeRepository:
        AbstractRepository<long,Trade>,
        ITradeRepository
    {
        public const string ManagerIdEqualsFinder = "Trade.GetTradesWithManagerId";
        public const string TradeIdEqualsFinder = "Trade.GetTradesWithTradeId";

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        /// <param name="p"></param>
        /// 
        public 
        TradeRepository(IUnitOfWorkProvider p):
            base( p )
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.InsertTrade(Trade)"/>
        /// 
        public Trade 
        InsertTrade(Trade trade)
        {
            return base.Insert(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.UpdateTrade(Trade)"/>
        /// 
        public Trade 
        UpdateTrade(Trade trade)
        {
            return base.Update(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.RemoveTrade(Trade)"/>
        /// 
        public void 
        RemoveTrade(Trade trade)
        {
            base.Remove(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.RemoveTradesWithTradeIds(ISet{int})"/>
        /// 
        public void 
        RemoveTradesWithTradeIds(ISet<int> tradeIds)
        {
            INamedQuery<Trade> removeTrades = 
                base.GetNamedQuery( "Trade.RemoveTrades" );
            IList<int> removedIds = new List<int>();

            foreach (int tradeId in tradeIds)
            {
                removedIds.Add( tradeId );

                if ( removedIds.Count > 100 )
                {
                    removeTrades.SetInput( "removedIds",removedIds );
                    removeTrades.Execute();
                    removeTrades.Clear();
                    removedIds.Clear();
                }    
            }

            if ( removedIds.Count > 0 )
            {
                removeTrades.SetInput( "removedIds",removedIds );
                removeTrades.Execute();
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrade(int)"/>
        /// 
        public Trade 
        GetTrade(long tradeKey)
        {
            return base.GetUnique(tradeKey);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTradeByTradeId(int)"/>
        /// 
        public Trade 
        GetTradeByTradeId(int tradeId)
        {
            return base.GetUnique( t => t.TradeId == tradeId );
        }

        //////////////////////////////////////////////////////////////////////
        /// <see 
        /// cref="ITradeRepository
        ///         .GetTradeContainingAccountAllocation(int)"/>
        /// 
        public Trade 
        GetTradeContainingAccountAllocation(int accountAllocId)
        {
            return 
                base.GetUnique(
                    t => t.AccountAllocations.Any(
                        a => a.AccountAllocationId == accountAllocId));
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTrades(Expression<Func<Trade,bool>> predicate)
        {
            return base.GetAll(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTradesWithManagerId(int managerId)
        {
            INamedQuery<Trade> finder = 
                base.GetNamedQuery(ManagerIdEqualsFinder);

            finder.SetInput( "id",managerId );
            return new List<Trade>(finder.GetAll());
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTradesWithTradeId(int tradeId)
        {
            INamedQuery<Trade> finder = base.GetNamedQuery(TradeIdEqualsFinder);

            finder.SetInput("tradeId", tradeId);
            return new List<Trade>(finder.GetAll());
        } 

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetAllTrades()"/>
        public IList<Trade> 
        GetAllTrades()
        {
            return base.GetAll();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTrade(long tradeKey)
        {
            return base.HasUnique(tradeKey);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTradeWithTradeId(int tradeId)
        {
            INamedQuery<Trade> finder = 
                base.GetNamedQuery( "Trade.HasTradeWithTradeId" );

            finder.SetInput( "id",tradeId );
            return finder.HasUnique();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTradeWithCusip(string cusip)
        {
            INamedQuery<Trade> finder =
                base.GetNamedQuery(TradeIdEqualsFinder);

            finder.SetInput("cusip", cusip);
            return finder.HasUnique();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrades(Predicate<Trade>)"/>
        /// 
        public bool 
        HasTrades(Expression<Func<Trade, bool>> predicate)
        {
            return base.HasAny(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        /// <param name="trade"></param>
        /// 
        public static long 
        GetKey(Trade trade)
        {
            return trade.TradeKey;
        }
    }
}

//  ##########################################################################
