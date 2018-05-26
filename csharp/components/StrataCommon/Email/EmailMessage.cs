//  ##########################################################################
//  # File Name: EmailMessage.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
    class EmailMessage:
        IEmailMessage
    {
        public String 
        From { get { return Imp.From.ToString(); } }

        public String 
        To { get { return Imp.To.ToString(); } }

        public String 
        Cc
        {
            get { return Imp.CC.ToString(); }
            set
            {
                MailAddressCollection cc = new MailAddressCollection();

                foreach (String a in value.Split( new char[] {','} ))
                    Imp.CC.Add( new MailAddress( a ) );
            }
        }

        public String 
        Subject
        {
            get { return Imp.Subject; }
            set { Imp.Subject = value; }
        }
        public String 
        Body
        {
            get { return Imp.Body; }
            set { Imp.Body = value; }
        }

        public MailMessage Imp {get;protected set;}

        public 
        EmailMessage(String from,String to)
        {
            Imp = new MailMessage(from,to);
        }

        public void 
        Dispose()
        {
            Imp.Dispose();
        }

        public void 
        Attach(IEmailAttachment attachment)
        {
            Imp.Attachments.Add( ((EmailAttachment)attachment).Imp );
        }
    }
}

//  ##########################################################################
