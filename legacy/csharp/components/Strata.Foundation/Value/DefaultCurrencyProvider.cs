using System;
using System.Collections.Generic;

namespace Strata.Foundation.Value
{
    /// <summary>
    /// A default currency provider for the <see cref="CurrencyManager"/>.  This provider reads currency itsTypeMappings
    /// from an IDH Currency file that is the CG standard for currencies.  The constructor will look for 
    /// a key in appSettings of "DefaultCurrencyFilePath" for the filepath or will use <see cref="Environment.CurrentDirectory"/>
    /// if no entry or value is set.
    /// </summary>
    public class DefaultCurrencyProvider : ICurrencyProvider
    {

        /// <summary>
        /// Creates a new <see cref="DefaultCurrencyProvider"/> instance.
        /// </summary>
        public DefaultCurrencyProvider()
        {
        }

        /// <summary>
        /// Gets an <see cref="IList{Currency}"/> from the provider.  Uses the <see cref="IdhFileReader"/> to retrieve
        /// the <see cref="Currency"/>s from the IDH currency file (CG standard)
        /// </summary>
        /// <returns>The provider's <see cref="Currency"/></returns> definitions.
        public IList<Currency> GetCurrencies()
        {
            IList<Currency> result = new List<Currency>();

            result.Add(new Currency(1,"USD","$"));
            result.Add(new Currency(2,"EUR","EUR"));
            result.Add(new Currency(3,"JPY","JPY"));
            
            return result;
        }
    }
}
