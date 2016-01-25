//  ##########################################################################
//  # File Name: FakeTextMessage.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;

namespace Strata.Integration.InMemoryMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class FakeMapMessage:
        FakeMessage,
        IMapMessage
    {
        public IDictionary<String,Object> Payload { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// $comments$
        /// </summary>
        public
        FakeMapMessage()
        {
            Payload = new Dictionary<String,Object>();
        }

        public IMapMessage 
        SetByte(String key,byte item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetBytes(String key,byte[] item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetBoolean(String key,bool item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetChar(String key,char item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetShort(String key,short item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetInt(String key,int item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetLong(String key,long item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetFloat(String key,float item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetDouble(String key,double item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetString(String key,String item)
        {
            Payload[key] = item;
            return this;
        }

        public IMapMessage 
        SetObject(String key,object item)
        {
            Payload[key] = item;
            return this;
        }

        public byte 
        GetByte(String key)
        {
            return (byte)Payload[key];
        }

        public byte[] 
        GetBytes(String key)
        {
           return (byte[])Payload[key];
        }

        public bool 
        GetBoolean(String key)
        {
           return (bool)Payload[key];
        }

        public char 
        GetChar(String key)
        {
           return (char)Payload[key];
        }

        public short 
        GetShort(String key)
        {
           return (short)Payload[key];
        }

        public int 
        GetInt(String key)
        {
           return (int)Payload[key];
        }

        public long 
        GetLong(String key)
        {
           return (long)Payload[key];
        }

        public float 
        GetFloat(String key)
        {
           return (float)Payload[key];
        }

        public double 
        GetDouble(String key)
        {
           return (double)Payload[key];
        }

        public String 
        GetString(String key)
        {
           return (String)Payload[key];
        }

        public Object 
        GetObject(String key)
        {
           return Payload[key];
        }

        public ICollection<String> 
        GetItemKeys()
        {
            return Payload.Keys;
        }

        public bool 
        HasItem(String key)
        {
            return Payload.ContainsKey( key );
        }
    }
}

//  ##########################################################################
