//  ##########################################################################
//  # File Name: IObjectReader.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Strata.Common.Serialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Supports the Serialization pattern for reading from 
    /// files, databases, streams, etc.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public interface IObjectReader
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads an int field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    int
        ReadRequiredInt(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a long field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    long
        ReadRequiredLong(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a BigDecimal field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    decimal
        ReadRequiredDecimal(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a boolean field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    bool
        ReadRequiredBoolean(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a string field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    string
        ReadRequiredString(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a Date field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
	    DateTime
        ReadRequiredDate(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a DateTime field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        DateTime
        ReadRequiredDateTime(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads an int field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        int?
        ReadNullableInt(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a long field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        long?
        ReadNullableLong(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a BigDecimal field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        decimal?
        ReadNullableDecimal(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a boolean field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        bool?
        ReadNullableBoolean(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a string field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        string
        ReadNullableString(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a Date field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        DateTime?
        ReadNullableDate(String name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads a DateTime field with the specified name from the source.
        /// if the value is null, an exception is thrown.
        /// </summary>
        /// 
        DateTime?
        ReadNullableDateTime(String name);
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets the current context for reading to the specified object type.
        /// </summary>
        /// 
	    void 
        PushRoot(ISerializable obj);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Removes an object from the stack of context objects.
        /// </summary>
        /// 
	    void 
        PopRoot();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the next record from the source. Returns false if at the end.
        /// </summary>
        /// 
	    bool 
        GetNext();
    }
}
