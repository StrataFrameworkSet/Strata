//  ##########################################################################
//  # File Name: ExceptionData.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Core.Transfer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [DataContract]
    public
    class ExceptionData
    {
        [DataMember]
        public string ExceptionType { get; set; }
        [DataMember]
        public Nullable<int> ExceptionCode { get; set; }
        [DataMember]
        public string ExceptionMessage { get; set; }
        [DataMember]
        public string StackTrace { get; set; }
        [DataMember]
        public ExceptionData Cause { get; set; }

        public
        ExceptionData()
        {
            ExceptionType = null;
            ExceptionCode = null;
            ExceptionMessage = null;
            StackTrace = null;
            Cause = null;
        }

        public static ExceptionData
        Of(Exception source)
        {
            if (source != null)
                return
                    new ExceptionData()
                    {
                        ExceptionType = source.GetType().Name,
                        ExceptionMessage = source.Message,
                        StackTrace = source.StackTrace,
                        Cause = Of(source.InnerException)
                    };

            return null;
        }
    }
}

//  ##########################################################################
