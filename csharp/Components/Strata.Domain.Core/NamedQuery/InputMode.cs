//  ##########################################################################
//  # File Name: InputMode.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents the mode (named or positional) for input parameters
    /// of <c>INamedQuery{T}</c> objects.
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
