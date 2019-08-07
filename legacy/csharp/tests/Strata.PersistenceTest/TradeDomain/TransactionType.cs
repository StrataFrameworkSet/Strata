//  ##########################################################################
//  # File Name: TransactionType.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    enum TransactionType
    {
        BUY = 1,
        SELL = 2
    }

    public static
    class TransactionTypeExtension
    {
        public const int BUY = 1;
        public const int SELL = 2;

        public static int 
        ToInt(this TransactionType t)
        {
            return t == TransactionType.BUY ? BUY : SELL;
        }
    }
}

//  ##########################################################################
