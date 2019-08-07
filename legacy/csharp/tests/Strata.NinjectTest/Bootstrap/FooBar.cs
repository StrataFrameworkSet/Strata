//  ##########################################################################
//  # File Name: FooBar.cs
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
    class FooBar:
        IFooBar
    {
        [DataMember]
        public IFoo   Foo { get; set; }

        [DataMember]
        [Inject]
        public IBar   Bar { get; set; }

        [DataMember]
        [Inject]
        [Named("test2")]
        public string Baz { get; set; }

        [Inject]
        public 
        FooBar(IFoo foo)
        {
            Foo = foo;
        }
    }
}

//  ##########################################################################
