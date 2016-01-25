//  ##########################################################################
//  # File Name: TransactionType.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
