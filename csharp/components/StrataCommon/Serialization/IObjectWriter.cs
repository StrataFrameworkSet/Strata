//  ##########################################################################
//  # File Name: IObjectWriter.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
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
    /// Supports the Serialization pattern for writing to 
    /// files, databases, streams, etc.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public interface IObjectWriter
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes an int field with the specified name from the source.
        /// </summary>
        /// 
        void 
        WriteInt(String name, int value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes a long field with the specified name from the source.
        /// </summary>
        /// 
        void 
        WriteLong(String name, long value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes a string field with the specified name from the source.
        /// </summary>
        /// 
        void
        WriteString(String name, String value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes a date field with the specified name from the source.
        /// </summary>
        /// 
        void 
        WriteDate(String name, DateTime date);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes a date field with the specified name from the source.
        /// </summary>
        /// 
        void
        WriteDateTime(String name, DateTime date);

    }
}
