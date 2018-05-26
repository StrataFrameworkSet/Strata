//  ##########################################################################
//  # File Name: ISerializable.cs
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
    /// Supports the Serialization pattern for reading/writing 
    /// files, databases, streams, etc.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public interface ISerializable
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads in the object's fields from the reader.
        /// </summary>
        /// 
	    void 
        ReadFrom(IObjectReader reader);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes the object's fields using the writer.
        /// </summary>
        /// 
	    void 
        WriteTo(IObjectWriter writer);
    }
}
