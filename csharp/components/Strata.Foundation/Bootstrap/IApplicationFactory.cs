//  ##########################################################################
//  # File Name: IApplicationFactory.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Injection;
using Strata.Foundation.Logging;
using System;

namespace Strata.Foundation.Bootstrap
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
