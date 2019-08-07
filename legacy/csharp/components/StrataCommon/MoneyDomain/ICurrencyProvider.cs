using System.Collections.Generic;

namespace Strata.Common.MoneyDomain
{
    public interface ICurrencyProvider
    {
    
        IList<Currency> GetCurrencies();
    }
}
