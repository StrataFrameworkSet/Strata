using Strata.Common.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Strata.Common.IdhSerialization
{
    public class 
    NdfCurrencyPair : ISerializable
    {
	    public int FromCurrencyId {get; protected set;}  
	    public string FromCurrencyCode {get; protected set;} 
	    public int ToCurrencyId {get; protected set;} 
	    public string ToCurrencyCode {get; protected set;} 
	    public decimal ExchangeRate {get; protected set;} 
	    public bool Active {get; protected set;} 
	    public DateTime ActiveDate {get; protected set;} 
	    public DateTime CreatedDateTime {get; protected set;} 
	    public long SecurityGuid {get; protected set;}

        //for testing nullable fields
        public int? NullableInt { get; protected set; }
        public long? NullableLong { get; protected set; }
        public decimal? NullableDecimal { get; protected set; }
        public bool? NullableBoolean { get; protected set; }
        public string NullableString { get; protected set; }
        public DateTime? NullableDate { get; protected set; }
        public DateTime? NullableDateTime { get; protected set; } 

	    public void ReadFrom(IObjectReader reader) 
        {
		    reader.PushRoot(this);
		    try {
			    FromCurrencyId = reader.ReadRequiredInt("fromCurrencyId");
                ToCurrencyId = reader.ReadRequiredInt("toCurrencyId");
                FromCurrencyCode = reader.ReadRequiredString("fromCurrencyCode");
                ToCurrencyCode = reader.ReadRequiredString("toCurrencyCode");
                ExchangeRate = reader.ReadRequiredDecimal("exchangeRate");
                Active = reader.ReadRequiredBoolean("active");
                ActiveDate = reader.ReadRequiredDate("activeDate");
                CreatedDateTime = reader.ReadRequiredDateTime("createdDateTime");
                SecurityGuid = reader.ReadRequiredLong("securityGuid");
                
                NullableInt = reader.ReadNullableInt("nullableInt");
                NullableLong = reader.ReadNullableLong("nullableLong");
                NullableDecimal = reader.ReadNullableDecimal("nullableDecimal");
                NullableBoolean = reader.ReadNullableBoolean("nullableBoolean");
                NullableString = reader.ReadNullableString("nullableString");
                NullableDate = reader.ReadNullableDate("nullableDate");
                NullableDateTime = reader.ReadNullableDateTime("nullableDateTime");
                 
            }
		    finally {
			    reader.PopRoot();
		    }
	    }

	    public void WriteTo(IObjectWriter writer) 
        {
		    //writer.WriteRoot(this);
		    writer.WriteInt("fromCurrencyId", FromCurrencyId);
		    writer.WriteString("fromCurrencyCode", FromCurrencyCode);
		    writer.WriteInt("toCurrencyId", ToCurrencyId);
		    writer.WriteString("toCurrencyCode", ToCurrencyCode);
	    }

    }
}
