//  ##########################################################################
//  # File Name: AbstractCurrencyExchanger.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class for currency exchangers. Provides the functionality for 
    /// processing exchanges. Subclasses must provide functionality for
    /// determining currency exchange rates.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractCurrencyExchanger:
        ICurrencyExchanger
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractCurrencyExchanger</c> instance.
        /// </summary>
        /// 
        public 
        AbstractCurrencyExchanger() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICurrencyExchanger.GetSupportedCurrencies()"/>
        /// </summary>
        /// 
        public abstract ISet<Currency> 
        GetSupportedCurrencies();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ICurrencyExchanger.Exchange(Currency,Money)"/>
        /// </summary>
        /// 
        public Money 
        Exchange(Currency currency,Money money)
        {
            return Exchange( currency,money,DateTime.Now );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see 
        /// cref="ICurrencyExchanger.Exchange(Currency,Money,DateTime)"/>
        /// </summary>
        /// 
        public Money 
        Exchange(Currency currency,Money money,DateTime date)
        {
		    Decimal amount       = money.Amount;
		    Decimal exchangeRate = GetExchangeRate( 
									  currency,
									  money.Currency,
									  date );
		
		    return new Money( currency,amount * exchangeRate );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines the exchange rate for the to/from currencies 
        /// on the specified date.
        /// </summary>
        /// 
        protected abstract Decimal
        GetExchangeRate(Currency to,Currency from,DateTime date);
    
    }
}

//  ##########################################################################
