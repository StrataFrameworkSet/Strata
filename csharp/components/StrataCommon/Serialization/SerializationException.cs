//  ##########################################################################
//  # File Name: SerializationException.cs
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
    /// Exception for the Serialization pattern for reading/writing
    /// files, databases, streams, etc.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public class SerializationException : Exception
    {
	    private static readonly long serialVersionUID = 1L;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates an exception object with a message and embedded exception.
        /// </summary>
        /// 
        public SerializationException(String message, Exception e) : base(message, e)
        {
		    
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates an exception object with the specified message.
        /// </summary>
        /// 
        public SerializationException(String message) : base(message)
        {
		    
	    }
    }
}
