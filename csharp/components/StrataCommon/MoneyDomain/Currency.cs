//  ##########################################################################
//  # File Name: Currency.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Serialization;

namespace Strata.Common.MoneyDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public sealed
    class Currency : ISerializable
    {
        public int CurrencyId { get; protected set; }
        public string CurrencyCode { get; protected set; }
        public string Symbol { get; protected set; }

        /// <summary>
        /// Creates a new <see cref="Currency"/> instance for use with <see cref="ISerializable.ReadFrom"/>.
        /// </summary>
        public Currency()
        {
            Symbol = string.Empty;
        }

        /// <summary>
        /// Creates a new <see cref="Currency"/> instance.
        /// </summary>
        /// <param name="id">The unique ID of this currency instance.</param>
        /// <param name="code">The ISO currency code of this currency instance.</param>
        /// <param name="symbol">The display symbol for this currency instance.</param>
        public Currency(int id, string code, string symbol)
        {
            CurrencyId = id;
            CurrencyCode = code;
            Symbol = symbol;
        }

        #region Equality

        public bool Equals(Currency other)
        {
            return CurrencyId == other.CurrencyId;
        }

        public override bool Equals(object obj)
        {
            return obj is Currency && Equals((Currency) obj);
        }

        public override int GetHashCode()
        {
            return CurrencyId;
        }

        public static bool operator ==(Currency first, Currency second)
        {
            if (ReferenceEquals(first, null))
            {
                return ReferenceEquals(second, null);
            }
            return !ReferenceEquals(second, null) && first.Equals(second);
        }

        public static bool operator !=(Currency first, Currency second)
        {
            if (ReferenceEquals(first, null))
            {
                return !ReferenceEquals(second, null);
            }
            if (ReferenceEquals(second, null))
            {
                return false;
            }
            return !first.Equals(second);
        }

        #endregion

        public void ReadFrom(IObjectReader reader)
        {
            try
            {
                reader.PushRoot(this);

                CurrencyId = reader.ReadRequiredInt("currencyId");
                CurrencyCode = reader.ReadRequiredString("currencyCode");
                Symbol = reader.ReadRequiredString("symbol");
            }
            finally
            {
                reader.PopRoot();
            }
        }

        public void WriteTo(IObjectWriter writer)
        {
            writer.WriteInt("currencyId", CurrencyId);
            writer.WriteString("currencyCode", CurrencyCode);
            writer.WriteString("symbol", Symbol);
        }
    }
}

//  ##########################################################################
