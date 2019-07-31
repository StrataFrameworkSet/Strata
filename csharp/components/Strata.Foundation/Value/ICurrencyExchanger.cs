//  ##########################################################################
//  # File Name: ICurrencyExchanger.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides functionality for converting <c>Money</c> objects
    /// between different <c>Currency</c>s.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface ICurrencyExchanger
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Returns the currencies supported by this exchanger.
        /// </summary>
        /// 
	    ISet<Currency>
	    GetSupportedCurrencies();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>Money</c> instance in the specified 
        /// currency and with an amount that is adjusted for the exchange
        /// rate between the specified currency and the currency of the
        /// original money amount.
        /// </summary>
        /// 
        /// <param name="currency">the to currency</param>
        /// <param name="money">money in the from currency</param>
        /// <returns>money coverted to the to currency</returns>
        /// 
        /// <precondition>
        /// this.GetSupportCurrencies().Contains(currency) == true
        /// </precondition>
        /// <exception cref="UnsupportedCurrencyException">
        /// currency is not supported
        /// </exception>
        /// 
	    Money
	    Exchange(Currency currency,Money money);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>Money</c> instance in the specified 
        /// currency and with an amount that is adjusted for the exchange
        /// rate on the specified date between the specified currency and 
        /// the currency of the original money amount.
        /// </summary>
        /// 
        /// <param name="currency">the to currency</param>
        /// <param name="money">money in the from currency</param>
        /// <param name="date">date to select exchange rate</param>
        /// <returns>money coverted to the to currency</returns>
        /// 
        /// <precondition>
        /// this.GetSupportCurrencies().Contains(currency) == true
        /// </precondition>
        /// <exception cref="UnsupportedCurrencyException">
        /// currency is not supported
        /// </exception>
        /// 
	    Money
	    Exchange(Currency currency,Money money,DateTime date);

    }
}

//  ##########################################################################
