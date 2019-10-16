//  ##########################################################################
//  # File Name: NamedAttribute.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NamedAttribute:
        #if USE_NINJECT
        Ninject.NamedAttribute
        #else
        Attribute
        #endif
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        ///  
        public 
        NamedAttribute(string name): 
            base() {}
    }
}

//  ##########################################################################
