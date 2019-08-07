//  ##########################################################################
//  # File Name: EmailDiagnosticReporter.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Email;
using System;

namespace Strata.Diagnostic.Core
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class EmailDiagnosticReporter:
        AbstractMessageBasedDiagnosticReporter
    {
        public String FromAddress { get; set; }
        public String ToAddress { get; set; }
        public String CcAddress { get; set; }
        public String Subject { get; set; }

        private readonly IEmailSession emailer;

        public 
        EmailDiagnosticReporter(IEmailSession e)
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
		    IEmailMessage message = 
                emailer.CreateEmailMessage( FromAddress,ToAddress );
		
            message.Cc = CcAddress;
            message.Subject = Subject;
		    message.Body = GetMessage();
		    emailer.Send( message );      
        }
    }
}

//  ##########################################################################
