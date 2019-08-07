//  ##########################################################################
//  # File Name: IFoo.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Value;

namespace Strata.Ninject.Bootstrap
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

        string
        DoSomething(bool throwException);

    }
}

//  ##########################################################################
