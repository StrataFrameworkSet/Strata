//  ##########################################################################
//  # File Name: IFooBar.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IFooBar
    {
        IFoo   Foo { get; set; }
        IBar   Bar { get; }
        string Baz { get; }
    }
}

//  ##########################################################################
