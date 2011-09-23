// ##########################################################################
// # File Name:	Money.java
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

import strata1.common.utility.Copyable;

import java.util.*;
import java.math.*;
import java.io.*;

/**
 * Money represented as a currency and an amount. {@code Money}
 * is a <a href="">Value Object</a> and is therefore immutable.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Money
	implements 	Copyable,
				Serializable
{
	private static final long serialVersionUID = 0;
	
	private final Currency itsCurrency;
	private final double   itsAmount;
	
	/************************************************************************
	 * Creates a new Money. 
	 * 
	 * @postcondition	this.getCurrency()==Currency.getInstance(Locale.US)
	 * @postcondition	this.getAmount() == 0.00
	 */
	public 
	Money()
	{
		this( Currency.getInstance( Locale.US ),0.00 );
	}
	
	/************************************************************************
	 * Creates a new Money. 
	 * 
	 * @postcondition	this.getCurrency()==Currency.getInstance(Locale.US)
	 * @postcondition	this.getAmount() == 0.00
	 */
	public 
	Money(Currency currency)
	{
		this( currency,0.00 );
	}

	/************************************************************************
	 * Creates a new Money. 
	 * 
	 * @param 			amount = the money's amount
	 * 
	 * @postcondition	this.getCurrency()==Currency.getInstance(Locale.US)
	 * @postcondition	this.getAmount() == amount
	 */
	public 
	Money(double amount)
	{
		this(Currency.getInstance(Locale.US),amount );
	}

	/************************************************************************
	 * Creates a new Money. 
	 *
	 * @param currency	the money's currency
	 * @param amount	the money's amount
	 * 
	 * @postcondition	this.getCurrency() == currency
	 * @postcondition	this.getAmount() == amount
	 */
	public 
	Money(Currency currency,double amount)
	{
		super();
		itsCurrency = currency;
		itsAmount   = amount;
	}

	/************************************************************************
	 * Creates a new Money. 
	 * 
	 * @param 			amount = the money's amount
	 * 
	 * @postcondition	this.getCurrency()==Currency.getInstance(Locale.US)
	 * @postcondition	this.getAmount() == amount
	 */
	public 
	Money(long amount)
	{
		this(Currency.getInstance(Locale.US),(double)amount );
	}

	/************************************************************************
	 * Creates a new Money. Note: Only used by {@code MoneyCalculator}.
	 *
	 * @param currency	the money's currency
	 * @param amount	the money's amount 
	 * 
	 * @postcondition	this.getCurrency() == currency
	 * @postcondition	this.getAmount() == amount
	 */ 
	public
	Money(Currency currency,long amount)
	{
		this( currency,(double)amount );
	}

	/************************************************************************
	 * Creates a new Money. Note: Only used by {@code MoneyCalculator}.
	 *
	 * @param other	    the money being copied
	 * 
	 * @postcondition	this.getCurrency() == other.getCurrency()
	 * @postcondition	this.getAmount() == other.getAmount()
	 */ 
	public
	Money(Money other)
	{
		this( other.itsCurrency,other.itsAmount );
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see Copyable#copy()
	 */
	@Override
	public Money 
	copy()
	{
		return new Money( this );
	}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object other)
    {
    	return (other instanceof Money) && equals( (Money)other );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode()
    {
    	int hash = 7;
    	
    	hash = 31 * hash + itsCurrency.hashCode();
    	hash = 31 * hash + new Double( itsAmount ).hashCode();
    	return hash;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
    	int        decimalPlaces = itsCurrency.getDefaultFractionDigits();
    	BigDecimal formatter = BigDecimal.valueOf( itsAmount )
    									 .setScale( 
    									      decimalPlaces,
    									      RoundingMode.HALF_UP );
    	
    	return itsCurrency.getSymbol() + formatter.toPlainString();
    }

    /************************************************************************
	 * @return	the {@code Money}'s currency 
	 */
	public Currency
	getCurrency()
	{
		return itsCurrency;
	}
	
	/************************************************************************
	 * @return the {@code Money}'s amount
	 */
	public double
	getAmount()
	{
		return itsAmount;
	}
	
	/************************************************************************
	 * Compares {@code Money} objects for equality.
	 *
	 * @param other
	 * @return
	 */
	public boolean
	equals(Money other)
	{
		return 
			(this == other) ||
			(itsCurrency.equals( other.getCurrency() ) && 
			itsAmount == other.itsAmount);
	}
}


// ##########################################################################
