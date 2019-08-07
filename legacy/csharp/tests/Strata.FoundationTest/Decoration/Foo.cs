//  ##########################################################################
//  # File Name: Foo.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class Foo:
        IFoo
    {
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
