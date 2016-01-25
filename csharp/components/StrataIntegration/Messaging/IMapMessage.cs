//  ##########################################################################
//  # File Name: IMapMessage.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace Strata.Integration.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IMapMessage:
        IMessage
    {
        IMapMessage
        SetByte(String key,byte item);

        IMapMessage
        SetBytes(String key,byte[] item);

        IMapMessage
        SetBoolean(String key,bool item);

        IMapMessage
        SetChar(String key,char item);

        IMapMessage
        SetShort(String key,short item);

        IMapMessage
        SetInt(String key,int item);

        IMapMessage
        SetLong(String key,long item);

        IMapMessage
        SetFloat(String key,float item);

        IMapMessage
        SetDouble(String key,double item);

        IMapMessage
        SetString(String key,String item);

        IMapMessage
        SetObject(String key,Object item);

        byte
        GetByte(String key);

        byte[]
        GetBytes(String key);

        bool
        GetBoolean(String key);

        char
        GetChar(String key);

        short
        GetShort(String key);

        int
        GetInt(String key);

        long
        GetLong(String key);

        float
        GetFloat(String key);

        double
        GetDouble(String key);

        String
        GetString(String key);

        Object
        GetObject(String key);

        ICollection<String>
        GetItemKeys();

        bool
        HasItem(String key);

    }
}

//  ##########################################################################
