using System.Configuration;
using System.Linq;
using System.Net.Mail;
using System.Net.Mime;

namespace Strata.Common.Utility
{
    public class EmailTools
    {
        private const string SmtpServer = "SMTP.CAPGROUP.COM";
        private const string SmtpServerConfigKey = "SmtpServerRelay";

        public static void SendMailWithAttachment(string subject, string to, string from, string body, string attachment)
        {
            SendMailWithAttachment(subject, to, from, body, attachment, false);
        }

        public static void SendMailHtmlWithAttachment(string subject, string to, string from, string body, string attachment)
        {
            SendMailWithAttachment(subject, to, from, body, attachment, true);
        }

        public static void SendMailHtmlWithAttachments(string subject, string to, string from, string body, string[] attachment)
        {
            SendMailWithAttachments(subject, to, from, body, attachment, true);
        }

        public static void SendMailWithAttachment(string subject, string to, string from, string body, string attachment, bool isHtml)
        {
            if (string.IsNullOrEmpty(attachment))
            {
                SendMailWithAttachments(subject, to, from, body, null, isHtml);
            }
            else
            {
                var attachments = new string[1];
                attachments[0] = attachment;
                SendMailWithAttachments(subject, to, from, body, attachments, isHtml);
            }
        }

        public static void SendMailWithAttachments(string subject, string to, string from, string body, string[] attachments, bool isHtml)
        {
            var smtpServer = ConfigurationManager.AppSettings[SmtpServerConfigKey] ?? SmtpServer;
            var mail = new MailMessage(from, to, subject, body);
            var smtpClient = new SmtpClient(smtpServer);
            mail.IsBodyHtml = isHtml;
            mail.BodyEncoding = System.Text.Encoding.UTF8;

            if (attachments != null)
            {
                foreach (var attch in attachments.Select(item => new Attachment(item, MediaTypeNames.Application.Octet)))
                {
                    mail.Attachments.Add(attch);
                }
            }

            smtpClient.Send(mail);
        }

        public static void SendMail(string subject, string to, string from, string body)
        {
            SendMailWithAttachment(subject, to, from, body, null, false);
        }

        public static void SendMailHtml(string subject, string to, string from, string body)
        {
            SendMailWithAttachment(subject, to, from, body, null, true);
        }

    }
}
