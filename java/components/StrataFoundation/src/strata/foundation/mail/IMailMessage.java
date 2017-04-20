package strata.foundation.mail;

import strata.foundation.value.EmailAddress;

public 
interface IMailMessage
{
    
    IMailMessage
    setFrom(EmailAddress from);
    
    IMailMessage
    setTo(EmailAddress to);
    IMailMessage
    setCc(EmailAddress cc);
    
    IMailMessage
    setBcc(EmailAddress bcc);
    
    IMailMessage
    setText(String text);
}

// ##########################################################################
