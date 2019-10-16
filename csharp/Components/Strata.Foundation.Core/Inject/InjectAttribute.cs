//  ##########################################################################
//  # File Name: InjectAttribute.cs
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
    class InjectAttribute:
        #if USE_NINJECT
        Ninject.InjectAttribute
        #else
        Attribute
        #endif
    {
    }
}

//  ##########################################################################
