//  ##########################################################################
//  # File Name: DateConverter.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Strata.Foundation.Value;

namespace Strata.Foundation.Mapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class IsoDateConverter:
        IsoDateTimeConverter
    {
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override bool 
        CanConvert(Type type)
        {
            return 
                type == typeof(Date) || 
                base.CanConvert(type);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override object 
        ReadJson(
            JsonReader     reader,
            Type           objectType,
            object         existingValue,
            JsonSerializer serializer)
        {
            return 
                new Date(
                    (DateTime)
                        base.ReadJson(
                            reader,
                            objectType,
                            existingValue,
                            serializer));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public override void 
        WriteJson(JsonWriter writer,object value,JsonSerializer serializer)
        {
            if (value is Date)
                base.WriteJson(writer,((Date)value).ToDateTime(),serializer);
            else
                base.WriteJson(writer,value,serializer);
        }
    }
}

//  ##########################################################################
