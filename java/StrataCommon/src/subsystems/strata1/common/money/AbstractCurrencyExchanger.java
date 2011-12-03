// ##########################################################################
// # File Name:	AbstractCurrencyExchanger.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.money;

import strata1.common.datetime.DateTime;

import java.util.Currency;

/**
 * Base class for currency exchangers. Provides the functionality for 
 * processing exchanges. Subclasses must provide functionality for
 * determining currency exchange rates.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractCurrencyExchanger 
	implements CurrencyExchanger
{
	/************************************************************************
	 * Creates a new AbstractCurrencyExchanger. 
	 *
	 */
	public AbstractCurrencyExchanger()
	{
		super();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public Money 
	exchange(Currency currency,Money money)
	{
		return exchange( currency,money,new DateTime() );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public Money 
	exchange(Currency currency,Money money,DateTime date)
	{
		double amount       = money.getAmount();
		double exchangeRate = getExchangeRate( 
									  currency,
									  money.getCurrency(),
									  date );
		
		return new Money( currency,amount * exchangeRate );
	}

	/************************************************************************
	 * Returns the exchange rate for the specific currencies at the 
	 * specific time 
	 *
	 * @param 	to		currency converting to
	 * @param 	from	currency converting from
	 * @param 	when	time period exchange rate applied
	 * @return	exchange rate
	 */
	protected abstract double
	getExchangeRate(Currency to,Currency from,DateTime when);
}


// ##########################################################################
