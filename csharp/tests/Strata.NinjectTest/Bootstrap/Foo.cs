//  ##########################################################################
//  # File Name: Foo.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using Strata.Foundation.Value;
using System;
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
    class Foo:
        IFoo
    {
        [DataMember]
        public string FooName { get; protected set; }
        [DataMember]
        public Date Created { get; set; }
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

        public string
        DoSomething(bool throwException)
        {
            Console.Out.WriteLine("DoSomething");

            if (throwException)
                throw new Exception("Foo");

            return "Foo Return";

        }
    }
}

//  ##########################################################################
