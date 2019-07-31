//  ##########################################################################
//  # File Name: IFoo.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Value;
using System;
using System.Runtime.Serialization;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IFoo
    {
        string FooName { get; }
        Date   Created { get; }
    }
}

//  ##########################################################################
