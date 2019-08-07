//  ##########################################################################
//  # File Name: IEmailAttachment.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Email
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IEmailAttachment:
        IDisposable
    {
        String Name {get;}
    }
}

//  ##########################################################################
