//  ##########################################################################
//  # File Name: IEmailSession.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.Email
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Creates email messages and attachments and sends email messages.
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IEmailSession:
        IDisposable
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new email message object.
        /// </summary>
        /// 
        /// <param name="from">from email address</param>
        /// <param name="to">to email address</param>
        /// <returns>email message object</returns>
        /// 
        IEmailMessage 
        CreateEmailMessage(String from,String to);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new email attachment object.
        /// </summary>
        /// 
        /// <param name="filename">name of file to be attached</param>
        /// <returns>email attachment object</returns>
        /// 
        IEmailAttachment 
        CreateEmailAttachement(String filename);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sends an email.
        /// </summary>
        /// 
        /// <param name="message">email message to be sent</param>
        /// 
        void 
        Send(IEmailMessage message);
    }
}

//  ##########################################################################
