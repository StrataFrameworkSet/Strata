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
    /// A <c>IDiagnosticReport</c> that sends an email containing the
    /// results from running one or more diagnostics.
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

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>EmaailDiagnosticReporter</c> with the
        /// specified <c>SmtpClient</c>.
        /// </summary>
        /// 
        public
        EmailDiagnosticReporter(SmtpClient e)
        {
            emailer     = e;
            FromAddress = String.Empty;
            ToAddress   = String.Empty;
            CcAddress   = String.Empty;
            Subject     = String.Empty;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
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

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets the CC field on the email.
        /// </summary>
        /// 
        private void
        SetCc(MailAddressCollection cc,string addresses)
        {
            foreach (string a in addresses.Split(new char[] { ',' }))
                cc.Add(new MailAddress(a));
        }

    }
}

//  ##########################################################################
