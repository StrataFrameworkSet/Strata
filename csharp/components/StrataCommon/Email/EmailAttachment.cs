//  ##########################################################################
//  # File Name: EmailAttachment.cs
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
