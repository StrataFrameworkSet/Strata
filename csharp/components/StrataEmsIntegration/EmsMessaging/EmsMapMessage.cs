//  ##########################################################################
//  # File Name: EmsMapMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Integration.Messaging;
using TIBCO.EMS;
using System.Runtime.Serialization;

namespace Strata.EmsIntegration.EmsMessaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class EmsMapMessage:
        EmsMessage,
        IMapMessage
    {
        public override Message MessageImp {get{return MapMessageImp;}}
        public MapMessage       MapMessageImp { get; protected set; }

        public 
        EmsMapMessage(MapMessage imp)
        {
            MapMessageImp = imp;
        }  
      
        
        public IMapMessage 
        SetByte(String key,byte item)
        {
            MapMessageImp.SetByte( key,item );
            return this;
        }

        public IMapMessage 
        SetBytes(String key,byte[] item)
        {
            MapMessageImp.SetBytes( key,item );
            return this;
        }

        public IMapMessage 
        SetBoolean(String key,bool item)
        {
            MapMessageImp.SetBoolean( key,item );
            return this;
        }

        public IMapMessage 
        SetChar(String key,char item)
        {
            MapMessageImp.SetChar( key,item );
            return this;
        }

        public IMapMessage 
        SetShort(String key,short item)
        {
            MapMessageImp.SetShort( key,item );
            return this;
        }

        public IMapMessage 
        SetInt(String key,int item)
        {
            MapMessageImp.SetInt( key,item );
            return this;
        }

        public IMapMessage 
        SetLong(String key,long item)
        {
            MapMessageImp.SetLong( key,item );
            return this;
        }

        public IMapMessage 
        SetFloat(String key,float item)
        {
            MapMessageImp.SetFloat( key,item );
            return this;
        }

        public IMapMessage 
        SetDouble(String key,double item)
        {
            MapMessageImp.SetDouble( key,item );
            return this;
        }

        public IMapMessage 
        SetString(String key,String item)
        {
            MapMessageImp.SetString( key,item );
            return this;
        }

        public IMapMessage 
        SetObject(String key,object item)
        {
            MapMessageImp.SetObject( key,item );
            return this;
        }

        public byte 
        GetByte(String key)
        {
            return MapMessageImp.GetByte( key );
        }

        public byte[] 
        GetBytes(String key)
        {
            return MapMessageImp.GetBytes( key );
        }

        public bool 
        GetBoolean(String key)
        {
            return MapMessageImp.GetBoolean( key );
        }

        public char 
        GetChar(String key)
        {
            return MapMessageImp.GetChar( key );
        }

        public short 
        GetShort(String key)
        {
            return MapMessageImp.GetShort( key );
        }

        public int 
        GetInt(String key)
        {
            return MapMessageImp.GetInt( key );
        }

        public long 
        GetLong(String key)
        {
            return MapMessageImp.GetLong( key );
        }

        public float 
        GetFloat(String key)
        {
            return MapMessageImp.GetFloat( key );
        }

        public double 
        GetDouble(String key)
        {
            return MapMessageImp.GetDouble( key );
        }

        public String 
        GetString(String key)
        {
            return MapMessageImp.GetString( key );
        }

        public Object 
        GetObject(String key)
        {
            return MapMessageImp.GetObject( key );
        }

        public ICollection<String> 
        GetItemKeys()
        {
            HashSet<String> keys = new HashSet<String>();

            foreach (Object key in MapMessageImp.GetMapNames())
                keys.Add( key.ToString() );

            return keys;
        }

        public bool 
        HasItem(String key)
        {
            return MapMessageImp.ItemExists( key );
        }
    }
}

//  ##########################################################################
