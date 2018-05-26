//  ##########################################################################
//  # File Name: Bar.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Runtime.Serialization;
using SystemEx.Injection;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [DataContract]
    public
    class Bar:
        IBar
    {
        [DataMember]
        public int BarId { get; protected set; }

        [Inject]
        public
        Bar(int barId)
        {
            BarId = barId;
        }
    }
}

//  ##########################################################################
