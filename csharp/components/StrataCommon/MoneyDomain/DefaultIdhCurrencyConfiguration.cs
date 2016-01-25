using System.Collections.Generic;
using Strata.Common.IdhSerialization;

namespace Strata.Common.MoneyDomain
{
    public class DefaultIdhCurrencyConfiguration : IIdhFileConfiguration
    {
        private readonly Dictionary<string, int> indexMap;

        public DefaultIdhCurrencyConfiguration()
        {
            indexMap = new Dictionary<string, int>
            {
                {"currencyId", 0},
                {"currencyCode", 1},
                {"symbol", 2}
            };
        }

        public int GetAttributeIndex(string name)
        {
            return indexMap[name];
        }
    }
}
