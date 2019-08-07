using System.Collections.Generic;

namespace Strata.Foundation.Value
{
    public interface ICurrencyProvider
    {
    
        IList<Currency> GetCurrencies();
    }
}
