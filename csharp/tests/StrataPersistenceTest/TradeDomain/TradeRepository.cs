//  ##########################################################################
//  # File Name: TradeRepository.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
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
        TradeRepository(IRepositoryProvider<long,Trade> p):
            base( p )
        {
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.InsertTrade(Trade)"/>
        /// 
        public Trade 
        InsertTrade(Trade trade)
        {
            return Provider.InsertEntity(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.UpdateTrade(Trade)"/>
        /// 
        public Trade 
        UpdateTrade(Trade trade)
        {
            return Provider.UpdateEntity(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.RemoveTrade(Trade)"/>
        /// 
        public void 
        RemoveTrade(Trade trade)
        {
            Provider.RemoveEntity(trade);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.RemoveTradesWithTradeIds(ISet{int})"/>
        /// 
        public void 
        RemoveTradesWithTradeIds(ISet<int> tradeIds)
        {
            IRepositoryMethod removeTrades = 
                UnitOfWork.GetRepositoryMethod( "Trade.RemoveTrades" );
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
            return Provider.GetEntityByKey(tradeKey);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTradeByTradeId(int)"/>
        /// 
        public Trade 
        GetTradeByTradeId(int tradeId)
        {
            return 
                Provider.GetEntityByPredicate(
                    t => t.TradeId == tradeId );
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
                Provider.GetEntityByPredicate(
                    t => t.AccountAllocations.Any(
                        a => a.AccountAllocationId == accountAllocId));
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTrades(Func<Trade,bool> predicate)
        {
            return Provider.GetEntitiesByPredicate(predicate);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTradesWithManagerId(int managerId)
        {
            IFinder<Trade> finder = 
                Provider.GetFinder(ManagerIdEqualsFinder);

            finder.SetInput( "id",managerId );
            return new List<Trade>(finder.GetAll());
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetTrades(Predicate<Trade>)"/>
        /// 
        public IList<Trade> 
        GetTradesWithTradeId(int tradeId)
        {
            IFinder<Trade> finder = Provider.GetFinder(TradeIdEqualsFinder);

            finder.SetInput("tradeId", tradeId);
            return new List<Trade>(finder.GetAll());
        } 

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.GetAllTrades()"/>
        public IList<Trade> 
        GetAllTrades()
        {
            return Provider.GetAllEntities();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTrade(long tradeKey)
        {
            return Provider.HasEntityWithKey(tradeKey);
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTradeWithTradeId(int tradeId)
        {
            IFinder<Trade> finder = 
                Provider.GetFinder( "Trade.HasTradeWithTradeId" );

            finder.SetInput( "id",tradeId );
            return finder.HasUnique();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrade(int)"/>
        /// 
        public bool 
        HasTradeWithCusip(string cusip)
        {
            IFinder<Trade> finder =
                Provider.GetFinder(TradeIdEqualsFinder);

            finder.SetInput("cusip", cusip);
            return finder.HasUnique();
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="ITradeRepository.HasTrades(Predicate<Trade>)"/>
        /// 
        public bool 
        HasTrades(Func<Trade, bool> predicate)
        {
            return Provider.HasEntitiesWithPredicate(predicate);
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
