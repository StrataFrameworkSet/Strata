// ##########################################################################
// # File Name:	MoneyCalculator.java
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

import strata1.common.annotation.Postcondition;
import strata1.common.annotation.Precondition;

/**
 * Provides functionality for doing arithmetic operations on
 * {@code Money} objects. Uses {@code ICurrencyExchanger}
 * to convert {@code Money} of different currencies prior
 * to calculations.
 * 
 * @see ICurrencyExchanger
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MoneyCalculator
{
	private ICurrencyExchanger itsExchanger;
	
	/************************************************************************
	 * Creates a new MoneyCalculator. 
	 * 
	 * @param 			exchanger 	used to convert {@code Money} to 
	 * 								same currency during calculations
	 */
	@Precondition("exchanger != null")
	@Postcondition({
	    "this.getExchanger() == exchanger",
	    "this.getExchanger() != null"})
	public 
	MoneyCalculator(ICurrencyExchanger exchanger)
	{
		super();
		itsExchanger = exchanger;
	}

	/************************************************************************
	 * @return the {@code MoneyCalculator}'s currency exchanger
	 */
	public ICurrencyExchanger
	getExchanger()
	{
		return itsExchanger;
	}
	
	/************************************************************************
	 * Adds two {@code Money} objects together. 
	 *
	 * @param 	x {@code Money} being added
	 * @param 	y {@code Money} being added
	 * 
	 * @return	{@code Money} object that is the sum of x and y
	 */
	public Money
	add(Money x,Money y)
	{
		double xAmount = x.getAmount();
		double yAmount = 
			x.getCurrency() == y.getCurrency()
				? y.getAmount()
				: itsExchanger.exchange(x.getCurrency(),y).getAmount();
				
		return new Money( x.getCurrency(),xAmount+yAmount );
	}
	
	/************************************************************************
	 * Subtracts two {@code Money} objects. 
	 *
	 * @param 	x {@code Money} being subtracted
	 * @param 	y {@code Money} being subtracted
	 * 
	 * @return	{@code Money} object that is the difference of x and y
	 */
	public Money
	subtract(Money x,Money y)
	{
		double xAmount = x.getAmount();
		double yAmount = 
			x.getCurrency() == y.getCurrency()
				? y.getAmount()
				: itsExchanger.exchange(x.getCurrency(),y).getAmount();
				
		return new Money( x.getCurrency(),xAmount-yAmount );
	}
	
	/************************************************************************
	 * Multiplies a {@code Money} object by a number. 
	 *
	 * @param 	x {@code Money} being multiplied
	 * @param 	n the multiplier
	 * 
	 * @return	{@code Money} object that is 
	 * 			the multiplication of x by n
	 */
	public Money
	multiply(Money x,double n)
	{
		return new Money( x.getCurrency(),x.getAmount()*n );
	}
	
	
	/************************************************************************
	 * Multiplies a {@code Money} object by a number. 
	 *
	 * @param 	x {@code Money} being multiplied
	 * @param 	n the multiplier
	 * 
	 * @return	{@code Money} object that is 
	 * 			the multiplication of x by n
	 */
	public Money
	multiply(Money x,long n)
	{
		return  multiply( x,(double)n );
	}
	
	/************************************************************************
	 * Divides a {@code Money} object by a number. 
	 *
	 * @param 	x {@code Money} being divided
	 * @param 	n the divisor
	 * 
	 * @return	{@code Money} object that is 
	 * 			the division of x by n
	 */
	public Money
	divide(Money x,double n)
	{
		return new Money(x.getCurrency(),x.getAmount()/n );
	}
	
	/************************************************************************
	 * Divides a {@code Money} object by a number. 
	 *
	 * @param 	x {@code Money} being divided
	 * @param 	n the divisor
	 * 
	 * @return	{@code Money} object that is 
	 * 			the division of x by n
	 */
	public Money
	divide(Money x,long n)
	{
		return divide( x,(double)n );
	}
	
	/************************************************************************
	 * Compares two {@code Money} objects for equality. 
	 *
	 * @param 	x {@code Money} being compared
	 * @param 	y {@code Money} being compared
	 * 
	 * @return	true if amounts are the same
	 */
	public boolean
	isEqual(Money x,Money y)
	{
		double xAmount = x.getAmount();
		double yAmount = 
			x.getCurrency() == y.getCurrency()
				? y.getAmount()
				: itsExchanger.exchange(x.getCurrency(),y).getAmount();
		
		return xAmount == yAmount;
	}
	
	/************************************************************************
	 * Compares two {@code Money} objects for less than. 
	 *
	 * @param 	x {@code Money} being compared
	 * @param 	y {@code Money} being compared
	 * 
	 * @return	true if x amount < y amount
	 */
	public boolean
	isLess(Money x,Money y)
	{
		double xAmount = x.getAmount();
		double yAmount = 
			x.getCurrency() == y.getCurrency()
				? y.getAmount()
				: itsExchanger.exchange(x.getCurrency(),y).getAmount();
		
		return xAmount < yAmount;
	}
	
	/************************************************************************
	 * Compares two {@code Money} objects for less than or equal. 
	 *
	 * @param 	x {@code Money} being compared
	 * @param 	y {@code Money} being compared
	 * 
	 * @return	true if x amount <= y amount
	 */
	public boolean
	isLessOrEqual(Money x,Money y)
	{
		return isLess( x,y ) || isEqual( x,y );
	}
	
	/************************************************************************
	 * Compares two {@code Money} objects for greater than. 
	 *
	 * @param 	x {@code Money} being compared
	 * @param 	y {@code Money} being compared
	 * 
	 * @return	true if x amount > y amount
	 */
	public boolean
	isGreater(Money x,Money y)
	{
		return !isLessOrEqual( x,y );
	}
	
	/************************************************************************
	 * Compares two {@code Money} objects for greater than or equal. 
	 *
	 * @param 	x {@code Money} being compared
	 * @param 	y {@code Money} being compared
	 * 
	 * @return	true if x amount >= y amount
	 */
	public boolean
	isGreaterOrEqual(Money x,Money y)
	{
		return !isLess( x,y );
	}
}


// ##########################################################################
