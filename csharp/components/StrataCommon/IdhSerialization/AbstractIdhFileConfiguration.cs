//  ##########################################################################
//  # File Name: AbstractIdhFileConfiguration.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Serialization;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Strata.Common.IdhSerialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class for IDH file configurations.  Stores descriptive data
    /// about each field in an IDH file.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    abstract 
    public class AbstractIdhFileConfiguration
    {
	    protected Dictionary<String, int> attributeMap;
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Default constructor.
        /// </summary>
        /// 
	    protected AbstractIdhFileConfiguration() {
		    attributeMap = new Dictionary<String, int>();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Adds an attribute to the attribute map.
        /// </summary>
        /// 
        protected void 
        AddAttribute(String name, int index) {
		    attributeMap.Add(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the zero-base position in the file of the specified attribute.
        /// </summary>
        /// 
        public int 
        GetAttributeIndex(String name) 
        {
            if (attributeMap.ContainsKey(name))
            {
                return attributeMap[name];
            }
            else
		    {
			    throw new SerializationException("Could not find attribute " + name + " in file configuration.");
		    }
	    }

    }
}
