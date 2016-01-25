using System.Runtime.Serialization;

namespace Capgroup.Xwing.WcfIntegration.Messaging {
    /// <summary>
    /// Enumeration of message response acknowledgements.
    /// </summary>
    [DataContract]
    public enum AcknowledgeType {
        /// <summary>
        /// Indicates unsuccessful exeuction of the request.
        /// </summary>
        [EnumMember]
        Failure = 0,

        /// <summary>
        /// Indicates successful execution of the request.
        /// </summary>
        [EnumMember]
        Success = 1
    }
}
