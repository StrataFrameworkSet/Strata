//  ##########################################################################
//  # File Name: Currency.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################


using System;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class Currency:
        IEquatable<Currency>
    {
        public int    CurrencyId { get; protected set; }
        public string CurrencyCode { get; protected set; }
        public string Symbol { get; protected set; }

        public 
        Currency()
        {
            Symbol = string.Empty;
        }


        public 
        Currency(int id, string code, string symbol)
        {
            CurrencyId = id;
            CurrencyCode = code;
            Symbol = symbol;
        }

        public 
        Currency(Currency other)
        {
            CurrencyId = other.CurrencyId;
            CurrencyCode = other.CurrencyCode;
            Symbol = other.Symbol;
        }


        public bool 
        Equals(Currency other)
        {
            return CurrencyCode == other.CurrencyCode;
        }

        public override bool 
        Equals(object obj)
        {
            return obj is Currency && Equals((Currency) obj);
        }

        public override int 
        GetHashCode()
        {
            return CurrencyId;
        }

        public static bool 
        operator==(Currency first, Currency second)
        {
            if (ReferenceEquals(first, null))
                return ReferenceEquals(second, null);

            return !ReferenceEquals(second, null) && first.Equals(second);
        }

        public static bool 
        operator!=(Currency first, Currency second)
        {
            if (ReferenceEquals(first, null))
                return !ReferenceEquals(second, null);

            if (ReferenceEquals(second, null))
                return false;

            return !first.Equals(second);
        }

    }
}

//  ##########################################################################
