//  ##########################################################################
//  # File Name: IBootstrapper.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Core.Inject;
using System;

namespace Strata.Foundation.Core.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IBootstrapper
    {
        IContainer Container { get; }

        void
        Run(IApplicationFactory factory);

        void
        Run(IApplicationFactory factory,string[] arguments);
    }
}

//  ##########################################################################
