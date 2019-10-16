using System.Collections.Generic;

namespace Strata.Foundation.Core.Value
{
    public interface ICurrencyProvider
    {
    
        IList<Currency> GetCurrencies();
    }
}
