//  ##########################################################################
//  # File Name: EmailDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Net.Mail;

namespace Strata.Diagnostic.Core.Report
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class EmailDiagnosticReporter:
        AbstractMessageBasedDiagnosticReporter
    {
        public string FromAddress { get; set; }
        public string ToAddress { get; set; }
        public string CcAddress { get; set; }
        public string Subject { get; set; }

        private readonly SmtpClient emailer;

        public 
        EmailDiagnosticReporter(SmtpClient e)
        {
            emailer     = e;
            FromAddress = String.Empty;
            ToAddress   = String.Empty;
            CcAddress   = String.Empty;
            Subject     = String.Empty;
        }

        public override void 
        EndReport()
        {
		    MailMessage message = 
                new MailMessage(FromAddress,ToAddress);
		
            SetCc(message.CC,CcAddress);
            message.Subject = Subject;
		    message.Body = GetMessage();
		    emailer.Send( message );      
        }

        private void
        SetCc(MailAddressCollection cc,string addresses)
        {
            foreach (String a in addresses.Split(new char[] { ',' }))
                cc.Add(new MailAddress(a));
        }

    }
}

//  ##########################################################################
