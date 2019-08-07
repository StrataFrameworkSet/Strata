// ##########################################################################
// # File Name:	ICurrencyExchanger.java
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
import java.util.Collection;
import java.util.Currency;

/**
 * Provides functionality for converting {@code Money} object
 * between different {@code Currency}s.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ICurrencyExchanger
{
	/************************************************************************
	 * @return	a collection of the currencies supported 
	 *			by this {@code ICurrencyExchanger}.
	 */
	public Collection<Currency>
	getSupportedCurrencies();
	
	/************************************************************************
	 * Converts money in one currency into the equivalent the specified 
	 * currency at the current exchange rate.
	 *
	 * @param 	currency	the "to" currency
	 * @param 	money		the money amount in the "from" currency
	 * @return	money amount in the "to" currency 
	 */
	public Money
	exchange(Currency currency,Money money);
	
	/************************************************************************
	 * Converts money in one currency into the equivalent the specified 
	 * currency at the exchange rate on the specified date.
	 *
	 * @param 	currency	the "to" currency
	 * @param 	money		the money amount in the "from" currency
	 * @param	date		the date of the effective exchange rate
	 * @return	money amount in the "to" currency 
	 */
	public Money
	exchange(Currency currency,Money money,DateTime date);
}


// ##########################################################################
