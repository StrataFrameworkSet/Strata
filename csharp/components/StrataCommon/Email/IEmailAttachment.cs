//  ##########################################################################
//  # File Name: IEmailAttachment.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.Email
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
