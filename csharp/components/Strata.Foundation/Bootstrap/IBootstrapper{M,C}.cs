//  ##########################################################################
//  # File Name: IBootstrapper{M,C}.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Injection;
using System;

namespace Strata.Foundation.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IBootstrapper<M,C>
        where M: class
        where C: class
    {
        C Container { get; }

        void
        Run(IApplicationFactory<M,C> factory);

        void
        Run(IApplicationFactory<M,C> factory,string[] arguments);
    }
}

//  ##########################################################################
