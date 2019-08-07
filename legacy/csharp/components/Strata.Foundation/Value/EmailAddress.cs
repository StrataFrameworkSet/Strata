//  ##########################################################################
//  # File Name: EmailAddress.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;
using System.Text.RegularExpressions;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class EmailAddress
    {
        [DataMember]
        private string email;

        private const string EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        EmailAddress() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        EmailAddress(string emailAddress)
        {
            ValidateEmailAddress(emailAddress);
            email = emailAddress;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override string
        ToString()
        {
            return email;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private static void
        ValidateEmailAddress(string input)
        {
            if (!CheckEmailAddress(input))
                throw
                    new ArgumentException(
                        "Unregconized phone format: " + input);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private static bool
        CheckEmailAddress(string input)
        {
            if (Regex.IsMatch(input,EMAIL_PATTERN))
                return true;

            return false;
        }
    }
}

//  ##########################################################################
