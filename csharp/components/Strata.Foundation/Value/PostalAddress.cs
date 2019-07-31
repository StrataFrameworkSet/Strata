//  ##########################################################################
//  # File Name: PostalAddress.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class PostalAddress
    {
        [DataMember]
        public string StreetAddress { get; set; }
        [DataMember]
        public string City { get; set; }
        [DataMember]
        public string State { get; set; }
        [DataMember]
        public string PostalCode { get; set; }
        [DataMember]
        public string CountryCode { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PostalAddress() {}

    }
}

//  ##########################################################################
