﻿//  ##########################################################################
//  # File Name: JsonObjectMapper.cs
//  ##########################################################################

using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.IO;
using Newtonsoft.Json;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class JsonObjectMapper<T>:
        IObjectMapper<T,string>
    {
        private JsonSerializer             itsImp;
        private IDictionary<string,string> itsTypeMappings;

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        JsonObjectMapper():
            this(
                new JsonSerializer()
                {
                    NullValueHandling = NullValueHandling.Include,
                    DateFormatHandling = DateFormatHandling.IsoDateFormat,
                    DateTimeZoneHandling = DateTimeZoneHandling.Utc,
                    FloatFormatHandling = FloatFormatHandling.DefaultValue,
                    Formatting = Formatting.None,
                    TypeNameHandling = TypeNameHandling.Objects,
                    TypeNameAssemblyFormatHandling = TypeNameAssemblyFormatHandling.Simple,
                    StringEscapeHandling = StringEscapeHandling.Default
                }) {}

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        JsonObjectMapper(JsonSerializer imp)
        {
            itsImp = imp;
            itsTypeMappings = new ConcurrentDictionary<string,string>();
        }

        //////////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public string 
        ToPayload(T source)
        {
            TextWriter writer = new StringWriter();

            itsImp.Serialize(new JsonTextWriter(writer),source);
            return writer.ToString();
        }

        //////////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public S 
        ToObject<S>(string payload) 
            where S: T
        {
            TextReader reader = new StringReader(PreProcess(payload));

            return itsImp.Deserialize<S>(new JsonTextReader(reader));
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public JsonObjectMapper<T>
        InsertMapping(string sourceType,Type destType)
        {
            itsTypeMappings.Add(sourceType,destType.AssemblyQualifiedName);
            return this;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public JsonObjectMapper<T>
        ClearMappings()
        {
            itsTypeMappings.Clear();
            return this;
        }

        //////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected string
        PreProcess(string input)
        {
            string output;

            output = input.Replace("@class","$type");
            output = output.Replace("__type","$type");

            foreach (KeyValuePair<string,string> mapping in itsTypeMappings)
                output = output.Replace(mapping.Key,mapping.Value);

            return output;
        }

    }
}

//  ##########################################################################
