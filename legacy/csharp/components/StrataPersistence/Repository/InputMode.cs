//  ##########################################################################
//  # File Name: InputMode.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents the mode (named or positional) for input parameters
    /// of <c>IFinder{T}</c> objects.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    enum InputMode
    {
        NOT_INITIALIZED,
        NAMED,
        POSITIONAL
    }
}

//  ##########################################################################
