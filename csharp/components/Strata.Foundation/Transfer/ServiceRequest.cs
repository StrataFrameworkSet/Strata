//  ##########################################################################
//  # File Name: ServiceRequest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Transfer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class ServiceRequest
    {
        [DataMember]
        public Guid CorrelationId { get; set; }
        [DataMember]
        public long SentTimestamp { get; set; }
        [DataMember]
        public long ReceivedTimestamp { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        ServiceRequest()
        {
            CorrelationId = Guid.NewGuid();
            SentTimestamp = 0;
            ReceivedTimestamp = 0;
        }
    }
}

//  ##########################################################################
