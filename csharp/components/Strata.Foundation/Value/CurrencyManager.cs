using System.Collections.Generic;
using System.Linq;

namespace Strata.Foundation.Value
{
    /// <summary>
    /// In the flyweight pattern, the <see cref="CurrencyManager"/> acts as the flyweight factory, handling
    /// requests for instances of the flyweight, <see cref="Currency"/>.  This class produces <see cref="Currency"/>
    /// instances based on the mapping provider it is given.  
    /// </summary>
    public class CurrencyManager
    {
        // Cache each dictionary for search speed.
        private static IDictionary<int, Currency> _idLookup;
        private static IDictionary<string, Currency> _codeLookup;
        private static IDictionary<string, Currency> _symbolLookup;

        private static readonly object _syncRoot = new object();

        /// <summary>
        /// Creates a new <see cref="CurrencyManager"/> instance using a <see cref="DefaultCurrencyProvider"/>.
        /// </summary>
        static CurrencyManager()
        {
            SetDataSource(new DefaultCurrencyProvider().GetCurrencies());
        }

        /// <summary>
        /// Sets the data for the <see cref="CurrencyManager"/> to use. 
        /// </summary>
        /// <param name="dataList">The currencies to set to the dictionary caches.</param>
        public static void SetDataSource(IList<Currency> dataList)
        {
            lock (_syncRoot)
            {
                _idLookup = dataList.ToDictionary(c => c.CurrencyId, c => c);
                _codeLookup = dataList.ToDictionary(c => c.CurrencyCode, c => c);
                _symbolLookup = dataList.ToDictionary(c => c.Symbol, c => c);
            }
        }

        /// <summary>
        /// Gets the <see cref="Currency"/> instance for a given ID.
        /// </summary>
        /// <param name="id">The ID of the <see cref="Currency"/> to get.</param>
        /// <returns>The <see cref="Currency"/> instance for the given ID.</returns>
        public static Currency GetInstanceById(int id)
        {
            lock (_syncRoot)
            {
                return _idLookup[id];
            }
        }

        /// <summary>
        /// Gets the <see cref="Currency"/> instance for a given ISO code.
        /// </summary>
        /// <param name="code">The ISO code of the <see cref="Currency"/> to get.</param>
        /// <returns>The <see cref="Currency"/> instance for the given ISO code.</returns>
        public static Currency GetInstanceByCode(string code)
        {
            lock (_syncRoot)
            {
                return _codeLookup[code];
            }
        }

        /// <summary>
        /// Gets the <see cref="Currency"/> instance for a given symbol (ie, $).
        /// </summary>
        /// <param name="symbol">The symbol of the <see cref="Currency"/> to get.</param>
        /// <returns>The <see cref="Currency"/> instance for the given symbol.</returns>
        public static Currency GetInstanceBySymbol(string symbol)
        {
            lock (_syncRoot)
            {
                return _symbolLookup[symbol];
            }
        }
    }
}
