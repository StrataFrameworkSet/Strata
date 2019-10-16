//  ##########################################################################
//  # File Name: IApplicationFactory.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Core.Inject;
using Strata.Foundation.Core.Logging;
using System;

namespace Strata.Foundation.Core.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IApplicationFactory
    {
        IBinder<ILogger>
        CreateLoggerBinder(ISourceBindingBuilder<ILogger> builder);

        IBinder<IStartStopController>
        CreateControllerBinder(
            ISourceBindingBuilder<IStartStopController> builder);

        IContainer
        CreateContainer();
    }
}

//  ##########################################################################
