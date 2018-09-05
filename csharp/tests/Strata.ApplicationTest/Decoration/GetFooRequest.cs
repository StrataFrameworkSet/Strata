//  ##########################################################################
//  # File Name: GetFooRequest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Transfer;
using System;
using System.Runtime.Serialization;

namespace Strata.Application.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [DataContract]
    public
    class GetFooRequest:
        ServiceRequest
    {
        [DataMember]
        public string    Foo { get; set; }
        [DataMember]
        public Exception Throw { get; set; }
    }
}

//  ##########################################################################
