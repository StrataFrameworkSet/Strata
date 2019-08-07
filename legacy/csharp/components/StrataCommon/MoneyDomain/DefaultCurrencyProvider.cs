using System;
using System.Collections.Generic;
using System.Configuration;
using Strata.Common.IdhSerialization;

namespace Strata.Common.MoneyDomain
{
    /// <summary>
    /// A default currency provider for the <see cref="CurrencyManager"/>.  This provider reads currency mappings
    /// from an IDH Currency file that is the CG standard for currencies.  The constructor will look for 
    /// a key in appSettings of "DefaultCurrencyFilePath" for the filepath or will use <see cref="Environment.CurrentDirectory"/>
    /// if no entry or value is set.
    /// </summary>
    public class DefaultCurrencyProvider : ICurrencyProvider
    {
        private const string IDH_FILE_PATH_KEY = "DefaultCurrencyFilePath";
        private readonly string idhFilePath;

        /// <summary>
        /// Creates a new <see cref="DefaultCurrencyProvider"/> instance.
        /// </summary>
        public DefaultCurrencyProvider()
        {
            idhFilePath = string.IsNullOrEmpty(ConfigurationManager.AppSettings[IDH_FILE_PATH_KEY])
                ? Environment.CurrentDirectory
                : ConfigurationManager.AppSettings[IDH_FILE_PATH_KEY];
        }

        /// <summary>
        /// Gets an <see cref="IList{Currency}"/> from the provider.  Uses the <see cref="IdhFileReader"/> to retrieve
        /// the <see cref="Currency"/>s from the IDH currency file (CG standard)
        /// </summary>
        /// <returns>The provider's <see cref="Currency"/></returns> definitions.
        public IList<Currency> GetCurrencies()
        {
            IList<Currency> result = new List<Currency>();
            IdhFileReader reader = null;
            try
            {
                reader = new IdhFileReader(idhFilePath)
                    .InsertConfiguration(typeof (Currency), new DefaultIdhCurrencyConfiguration());            
                reader.Open();

                while (reader.GetNext())
                {
                    var currency = new Currency();
                    currency.ReadFrom(reader);
                    result.Add(currency);
                }
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
            }
            return result;
        }
    }
}
