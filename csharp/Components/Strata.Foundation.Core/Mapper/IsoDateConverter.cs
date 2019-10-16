//  ##########################################################################
//  # File Name: IsoDateConverter.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using Strata.Foundation.Core.Value;

namespace Strata.Foundation.Core.Mapper
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
            Type           type,
            object         value,
            JsonSerializer serializer)
        {
            if (type == typeof(Date))
                return 
                    new Date(
                        (DateTime)
                            base.ReadJson(
                                reader,
                                type,
                                value,
                                serializer));

            return base.ReadJson(reader,type,value,serializer);
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
