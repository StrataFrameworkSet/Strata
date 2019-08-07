//  ##########################################################################
//  # File Name: Foo.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Value;
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
    class Foo:
        IFoo
    {
        [DataMember]
        public string   FooName { get; protected set; }
        [DataMember]
        public Date     Created { get; set; }
        [DataMember]
        public DateTime Modified { get; set; }

        [Inject]
        public 
        Foo([Named("test1")] string name)
        {
            FooName = name;
            Created = Date.Now;
            Modified = DateTime.Now;
        }
    }
}

//  ##########################################################################
