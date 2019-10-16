//  ##########################################################################
//  # File Name: PhoneNumber.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;
using System.Text.RegularExpressions;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class PhoneNumber
    {
        [DataMember]
        private string phone;

        private const string NANP_PATTERN = 
            "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
    
        private const string ITU_T_PATTERN = 
            "^\\+(?:[0-9] ?){6,14}[0-9]$";
    
        private const string EPP_PATTERN = 
            "^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$";

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PhoneNumber() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PhoneNumber(string phoneNumber)
        {
            ValidatePhoneNumber(phoneNumber);
            phone = phoneNumber;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override string
        ToString()
        {
            return phone;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private static void
        ValidatePhoneNumber(string input)
        {
            if (!CheckPhoneNumber(input))
                throw 
                    new ArgumentException(
                        "Unregconized phone format: " + input);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        private static bool
        CheckPhoneNumber(string input)
        {
            if (Regex.IsMatch(input,NANP_PATTERN))
                return true;

            if (Regex.IsMatch(input,ITU_T_PATTERN))
                return true;

            if (Regex.IsMatch(input,EPP_PATTERN))
                return true;

            return false;
        }
    }
}

//  ##########################################################################
