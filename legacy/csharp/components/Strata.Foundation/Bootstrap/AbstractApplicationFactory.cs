//  ##########################################################################
//  # File Name: AbstractApplicationFactory.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Injection;
using Strata.Foundation.Logging;
using Strata.Foundation.Utility;
using System.Collections.Generic;

namespace Strata.Foundation.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractApplicationFactory:
        IApplicationFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IBinder<ILogger> 
        CreateLoggerBinder(ISourceBindingBuilder<ILogger> builder)
        {
            return
                new ProviderBasedBinder<ILogger>(
                    builder,
                    new FuncBasedProvider<ILogger>(() => new NullLogger()),
                    AbstractModule.DefaultScope);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IBinder<IStartStopController>
        CreateControllerBinder(
            ISourceBindingBuilder<IStartStopController> builder)
        {
            return
                new TargetBasedBinder<
                    IStartStopController,
                    NullStartStopController>(
                        builder,
                        AbstractModule.DefaultScope);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual IContainer 
        CreateContainer()
        {
            IList<IModule> modules = new List<IModule>();

            modules.Add(CreateBootstrapModule());
            modules.AddAll(CreateModules());

            return CreateContainer(modules);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected virtual IModule
        CreateBootstrapModule()
        {
            return new BootstrapModule(this);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected abstract IList<IModule>
        CreateModules();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected abstract IContainer
        CreateContainer(IList<IModule> modules);
    }
}

//  ##########################################################################
