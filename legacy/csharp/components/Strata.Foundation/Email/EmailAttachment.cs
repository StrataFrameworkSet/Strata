//  ##########################################################################
//  # File Name: EmailAttachment.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Net.Mail;

namespace Strata.Foundation.Email
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public
    class EmailAttachment:
        IEmailAttachment
    {

        public String 
        Name
        {
            get { return Imp.Name; }
        }

        public Attachment 
        Imp {get;protected set;}

        public 
        EmailAttachment(String filename)
        {
            Imp = new Attachment( filename );
        }

        public 
        EmailAttachment(Attachment attachment)
        {
            Imp = attachment;
        }

        public void 
        Dispose()
        {
            Imp.Dispose();
        }

    }
}

//  ##########################################################################
