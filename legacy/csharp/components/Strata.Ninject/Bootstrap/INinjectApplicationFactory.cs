//  ##########################################################################
//  # File Name: INinjectApplicationFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using Ninject.Modules;
using Strata.Foundation.Bootstrap;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface INinjectApplicationFactory:
        IApplicationFactory<INinjectModule,IKernel> {}
}

//  ##########################################################################
