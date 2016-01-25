using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Strata.Common.IdhSerialization
{
    public class 
    NdfCurrencyFileConfiguration :
        AbstractIdhFileConfiguration,
        IIdhFileConfiguration
    {
        public NdfCurrencyFileConfiguration() : base()
        {
            AddAttribute("fromCurrencyId", 0);
            AddAttribute("fromCurrencyCode", 1);
            AddAttribute("toCurrencyId", 2);
            AddAttribute("toCurrencyCode", 3);
            AddAttribute("exchangeRate", 4);
            AddAttribute("active", 5);
            AddAttribute("activeDate", 6);
            AddAttribute("createdDateTime", 7);
            AddAttribute("securityGuid", 8);
            AddAttribute("nullableInt", 9);
            AddAttribute("nullableLong", 10);
            AddAttribute("nullableDecimal", 11);
            AddAttribute("nullableBoolean", 12);
            AddAttribute("nullableString", 13);
            AddAttribute("nullableDate", 14);
            AddAttribute("nullableDateTime", 15);

        }
    }
}
