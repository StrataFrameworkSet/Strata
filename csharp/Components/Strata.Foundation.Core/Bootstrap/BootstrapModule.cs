//  ##########################################################################
//  # File Name: BootstrapModule.cs
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
    class BootstrapModule:
        AbstractModule
    {
        private IApplicationFactory itsFactory;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        BootstrapModule(IApplicationFactory factory):
            base("BootstrapModule")
        {
            itsFactory = factory;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Initialize()
        {
            itsFactory
                .CreateLoggerBinder(Bind<ILogger>())
                .Bind();

            itsFactory
                .CreateControllerBinder(Bind<IStartStopController>())
                .Bind();
        }
    }
}

//  ##########################################################################
