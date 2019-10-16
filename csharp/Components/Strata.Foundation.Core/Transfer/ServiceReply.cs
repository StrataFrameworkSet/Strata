//  ##########################################################################
//  # File Name: ServiceReply.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Core.Transfer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class ServiceReply
    {
        [DataMember]
        public Guid   CorrelationId { get; set; }
        [DataMember]
        public long   SentTimestamp { get; set; }
        [DataMember]
        public long   ReceivedTimestamp { get; set; }
        [DataMember]
        public long   ExecutionMilliseconds { get; set; }
        [DataMember]
        public bool   IsSuccessful { get; set; }
        [DataMember]
        public string SuccessMessage { get; set; }
        [DataMember]
        public string ExceptionType { get; set; }
        [DataMember]
        public string FailureMessage { get; set; }
        [DataMember]
        public string StackTrace { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        ServiceReply()
        {
            SentTimestamp         = 0;
            ReceivedTimestamp     = 0;
            ExecutionMilliseconds = 0;
            IsSuccessful          = true;
            SuccessMessage        = string.Empty;
            FailureMessage        = string.Empty;
            StackTrace            = string.Empty;
        }
    }
}

//  ##########################################################################
