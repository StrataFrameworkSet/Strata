//  ##########################################################################
//  # File Name: ICopyable.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides an interface for copyable objects that is meant to be 
    /// used with the <c>CopyExtension.Copy{T} extension."/></c>
    /// <seealso cref="CopyExtension.Copy{T}(T)"/>
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface ICopyable
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a copy of this object. 
        /// <seealso cref="CopyExtension.Copy{T}(T)"/>
        /// </summary>
        /// 
        /// <returns>
        /// copy of this object
        /// </returns>
        /// 
        ICopyable
        MakeCopy();
    }
}

//  ##########################################################################
