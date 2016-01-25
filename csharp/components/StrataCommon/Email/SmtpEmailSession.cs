//  ##########################################################################
//  # File Name: SmtpEmailSession.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Net.Mail;

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
    class SmtpEmailSession:
        IEmailSession
    {
        private SmtpClient client;

        public 
        SmtpEmailSession(String host)
        {
            client = new SmtpClient(host);
        }

        public void 
        Dispose()
        {
            client.Dispose();
        }

        public IEmailMessage 
        CreateEmailMessage(String from,String to)
        {
            return new EmailMessage( from,to );
        }

        public IEmailAttachment 
        CreateEmailAttachement(string filename)
        {
            return new EmailAttachment( filename );
        }

        public void 
        Send(IEmailMessage message)
        {
            client.Send( ((EmailMessage)message).Imp );
        }
    }
}

//  ##########################################################################
