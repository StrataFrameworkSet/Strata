//  ##########################################################################
//  # File Name: Money.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Foundation.Utility;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represent money (currency and amount) value objects.
    /// </summary>
    ///  
    public
    class Money:
        IEquatable<Money>,
        ICopyable
    {
        public Currency Currency { get; protected set; }
        public decimal  Amount { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        Money()
        {
            Currency = new Currency(1,"USD","$");
            Amount = 0.0m;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        Money(Currency currency, decimal amount)
        {
            if ( currency == default(Currency) )
                throw new ArgumentNullException("currency");

            Currency = new Currency(currency);
            Amount = amount;
        }
       
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public 
        Money(Money other)
        {
            Currency = other.Currency;
            Amount = other.Amount;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public bool 
        Equals(Money other)
        {
            if (other == null)
                return false;

            return (Currency == other.Currency)
                   && (Amount == other.Amount);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override bool  
        Equals(Object other)
        {               
            return other is Money && Equals( (Money)other );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override int  
        GetHashCode()
        {
		    int hash = 7;
		
		    hash = 31 * hash + Currency.GetHashCode();
		    hash = 31 * hash + Amount.GetHashCode();
		    return hash;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Add(Money other)
        {
            if (other == null)
                return this;

            if ((Currency != other.Currency))
                throw 
                    new ArgumentException("Cannot add different currencies");

            return new Money(Currency,Amount + other.Amount);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Add(Decimal n)
        {
            return new Money(Currency,Amount + n);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Subtract(Money other)
        {
            if (other == null)
                return this;

            if (Currency != other.Currency)
                throw 
                    new ArgumentException("Cannot subtract different currencies");

            return new Money(Currency,Amount - other.Amount);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Subtract(Decimal n)
        {
            return new Money(Currency,Amount - n);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Multiply(Decimal multiplier)
        {
            return new Money(Currency,Amount*multiplier);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public Money  
        Divide(Decimal divisor)
        {
            return new Money(Currency,Amount/divisor);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public ICopyable 
        MakeCopy()
        {
            return new Money(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static Money  
        operator + (Money x,Money y)
        {
            if (x == null && y == null)
                return null;

            if (x == null)
                return y;

            return x.Add( y );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static Money  
        operator - (Money x,Money y)
        {
            if (x == null && y == null)
                return null;

            if (x == null)
                return y;

            return x.Subtract( y );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static Money  
        operator * (Money x,Decimal y)
        {
            if (x == null)
                return null;

            return x.Multiply( y );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static Money  
        operator / (Money x,Decimal y)
        {
            if (x == null)
                return null;

            return x.Divide( y );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static bool
        operator == (Money x, Money y)
        {
            if (x is null && y is null)
                return true;

            if (x is null)
                return false;

            return x.Equals(y);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public static bool
        operator != (Money x, Money y)
        {
            return !(x == y);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public override string  
        ToString()
        {
            return "[Currency=" + Currency + ", Amount=" + Amount + "]";
        }

    }
}

//  ##########################################################################
