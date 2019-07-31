//  ##########################################################################
//  # File Name: Bar.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################


using Ninject;
using System.Runtime.Serialization;

namespace Strata.Ninject.Bootstrap
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
