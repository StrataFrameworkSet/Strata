//  ##########################################################################
//  # File Name: IEmailMessage.cs
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
    interface IEmailMessage:
        IDisposable
    {
        String From {get;}
        String To {get;}
        String Cc {get;set;}
        String Subject {get;set;}
        String Body {get;set;}
        
        void 
        Attach(IEmailAttachment attachment);
    }
}

//  ##########################################################################
