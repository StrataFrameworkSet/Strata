//  ##########################################################################
//  # File Name: IIdhFileConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Strata.Common.IdhSerialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides interface contract for file configurations  
    /// in support of the Serialization pattern.  Callers can retrieve 
    /// information about fields in the file.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public interface IIdhFileConfiguration
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the zero-based position of an attribute in file record.
        /// </summary>
        /// 
        int 
        GetAttributeIndex(String name);
    }
}
