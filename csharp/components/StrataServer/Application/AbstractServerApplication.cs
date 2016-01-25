//  ##########################################################################
//  # File Name: AbstractServerApplication.cs
//  # Copyright: 2014, Capital Group Companies, Inc.
//  ##########################################################################

using Strata.Common.Bootstrap;

namespace Strata.Server.Application
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Implements the <c>IServerApplication</c> interface. Dervived 
    /// classes must provide an <c>IServerFactory</c> and an 
    /// <c>IServerBootstrapper</c> to instantiate the server application.
    /// </summary>
    ///  
    public abstract
    class AbstractServerApplication:
        IServerApplication
    {
        private IBootstrapFactory factory;
        private IBootstrapper     bootstrapper;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>AbstractServerApplication</c> instance.
        /// </summary>
        /// 
        protected
        AbstractServerApplication(IBootstrapFactory f,IBootstrapper b)
        {
            factory      = f;
            bootstrapper = b;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Start()
        {
            bootstrapper.Run( factory );
            bootstrapper
                .StartupShutdownController
                .StartUp();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Stop()
        {
            bootstrapper
                .StartupShutdownController
                .ShutDown();
        }
    }
}

//  ##########################################################################
