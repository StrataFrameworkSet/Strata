using System;
using System.Collections.Specialized;
using System.Net.Mail;
using System.Text;
using Microsoft.Practices.EnterpriseLibrary.Common.Configuration;
using Microsoft.Practices.EnterpriseLibrary.ExceptionHandling;
using Microsoft.Practices.EnterpriseLibrary.ExceptionHandling.Configuration;

namespace Strata.Common.ExceptionManagement {
    [ConfigurationElementType(typeof(CustomHandlerData))]
    public class EmailExceptionHandler : IExceptionHandler {

        private readonly NameValueCollection parameters;
        private const string paramEmailSuffix = "EmailSuffix";
        private const string paramSmtpServer = "SmtpServer";
        private const string paramTo = "To";

        public EmailExceptionHandler(NameValueCollection collection) {
            parameters = collection;
        }

        #region Implementation of IExceptionHandler

        /// <summary>
        /// Wraps <see cref="EmailExceptionHandler"/> email logic around the unhandled exception.
        /// </summary>
        /// <param name="exception">The exception to send out in the email.</param>
        /// <param name="handlingInstanceId"></param>
        /// <returns>The exception.</returns>
        public Exception HandleException(Exception exception, Guid handlingInstanceId) {
            string emailSuffix = parameters[paramEmailSuffix];
            MailAddress from = new MailAddress("IDS.Service.at." + Environment.MachineName + emailSuffix);

            StringBuilder sb = new StringBuilder();
            sb.AppendLine("IDS Service encountered an unhandled exception:");
            sb.AppendLine();
            sb.AppendLine(exception.ToString());

            MailMessage email = new MailMessage {
                Body = sb.ToString(), 
                From = from, 
                Subject = "IDS Service @ " + Environment.MachineName + " : EXCEPTION DETAILS"
            };

            foreach (string name in parameters[paramTo].Split(',')) {
                email.To.Add(name + emailSuffix);
            }

            string smtpServer = parameters[paramSmtpServer];
            using (SmtpClient smtp = new SmtpClient(smtpServer)) {
                smtp.Send(email);
            }

            return exception;
        }

        #endregion

    }
}
