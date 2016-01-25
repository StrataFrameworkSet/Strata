using System;
using System.Collections.Generic;
using Strata.Persistence.InMemoryRepository;
using Strata.Persistence.TradeDomain;

namespace Strata.FlatFilePersistence
{
    public class TradeIdEqualsPredicate : IPredicate<Trade>
    {
        public bool Evaluate(Trade target, IDictionary<string, object> inputs)
        {
            return target.TradeId.Equals(Convert.ToInt32(inputs["tradeId"]));
        }
    }
}
