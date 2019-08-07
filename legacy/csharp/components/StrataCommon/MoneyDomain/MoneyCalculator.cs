//  ##########################################################################
//  # File Name: MoneyCalculator.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;

namespace Strata.Common.MoneyDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides functionality for doing arithmetic operations on
    /// <c>Money</c> objects. Uses <c>ICurrencyExchanger</c>
    /// to convert <c>Money</c> of different currencies prior
    /// to calculations.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class MoneyCalculator
    {
        public ICurrencyExchanger Exchanger {get;protected set;}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public 
        MoneyCalculator(ICurrencyExchanger exchanger)
        {
            Exchanger = exchanger;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Adds two <c>Money</c> objects together.
        /// </summary>
        /// 
        /// <param name="x">money being added</param>
        /// <param name="y">money being added</param>
        /// <returns>money that is the sum of x and y</returns>
        /// 
	    public Money
	    Add(Money x,Money y)
	    {
		    Decimal xAmount = x.Amount;
		    Decimal yAmount = 
			    x.Currency == y.Currency
				    ? y.Amount
				    : Exchanger.Exchange(x.Currency,y).Amount;
				
		    return new Money( x.Currency,xAmount+yAmount );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Subtracts two <c>Money</c> objects.
        /// </summary>
        /// 
        /// <param name="x">money being subtracted</param>
        /// <param name="y">money being subtracted</param>
        /// <returns>money that is the difference of x and y</returns>
        /// 
	    public Money
	    Subtract(Money x,Money y)
	    {
		    Decimal xAmount = x.Amount;
		    Decimal yAmount = 
			    x.Currency == y.Currency
				    ? y.Amount
				    : Exchanger.Exchange(x.Currency,y).Amount;
				
		    return new Money( x.Currency,xAmount-yAmount );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Multiplies a <c>Money</c> object by a number.
        /// </summary>
        /// 
        /// <param name="x">money being multiplied</param>
        /// <param name="n">the multiplier</param>
        /// <returns>money that is the multiplication of x by n</returns>
        /// 
	    public Money
	    Multiply(Money x,Decimal n)
	    {
		    return new Money( x.Currency,x.Amount*n );
	    }
		
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Multiplies a <c>Money</c> object by a number.
        /// </summary>
        /// 
        /// <param name="x">money being multiplied</param>
        /// <param name="n">the multiplier</param>
        /// <returns>money that is the multiplication of x by n</returns>
        /// 
	    public Money
	    Multiply(Money x,long n)
	    {
		    return  Multiply( x,(Decimal)n );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Divides a <c>Money</c> object by a number.
        /// </summary>
        /// 
        /// <param name="x">money being divided</param>
        /// <param name="n">the divisor</param>
        /// <returns>money that is the division of x by n</returns>
        /// 
	    public Money
	    Divide(Money x,Decimal n)
	    {
		    return new Money(x.Currency,x.Amount/n );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Divides a <c>Money</c> object by a number.
        /// </summary>
        /// 
        /// <param name="x">money being divided</param>
        /// <param name="n">the divisor</param>
        /// <returns>money that is the division of x by n</returns>
        /// 
	    public Money
	    Divide(Money x,long n)
	    {
		    return Divide( x,(Decimal)n );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Compares two <c>Money</c> objects for equality.
        /// </summary>
        /// 
        /// <param name="x">money being compared</param>
        /// <param name="y">money being compared</param>
        /// <returns>true if amounts are the same</returns>
        /// 
	    public bool 
	    IsEqual(Money x,Money y)
	    {
		    Decimal xAmount = x.Amount;
		    Decimal yAmount = 
			    x.Currency == y.Currency
				    ? y.Amount
				    : Exchanger.Exchange(x.Currency,y).Amount;
		
		    return xAmount == yAmount;
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Compares two <c>Money</c> objects for less than.
        /// </summary>
        /// 
        /// <param name="x">money being compared</param>
        /// <param name="y">money being compared</param>
        /// <returns>true if x amount is &lt; y amount</returns>
        /// 
	    public bool
	    IsLess(Money x,Money y)
	    {
		    Decimal xAmount = x.Amount;
		    Decimal yAmount = 
			    x.Currency == y.Currency
				    ? y.Amount
				    : Exchanger.Exchange(x.Currency,y).Amount;
		
		    return xAmount < yAmount;
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Compares two <c>Money</c> objects for less than or equal.
        /// </summary>
        /// 
        /// <param name="x">money being compared</param>
        /// <param name="y">money being compared</param>
        /// <returns>true if x amount is &lt;= y amount</returns>
        /// 
	    public bool
	    IsLessOrEqual(Money x,Money y)
	    {
		    return IsLess( x,y ) || IsEqual( x,y );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Compares two <c>Money</c> objects for greater than.
        /// </summary>
        /// 
        /// <param name="x">money being compared</param>
        /// <param name="y">money being compared</param>
        /// <returns>true if x amount is > y amount</returns>
        /// 
	    public bool
	    IsGreater(Money x,Money y)
	    {
		    return !IsLessOrEqual( x,y );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Compares two <c>Money</c> objects for greater than or equal.
        /// </summary>
        /// 
        /// <param name="x">money being compared</param>
        /// <param name="y">money being compared</param>
        /// <returns>true if x amount is > y amount</returns>
        /// 
	    public bool
	    IsGreaterOrEqual(Money x,Money y)
	    {
		    return !IsLess( x,y );
	    }

    }
}

//  ##########################################################################
