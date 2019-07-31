//  ##########################################################################
//  # File Name: NinjectApplicationFactory.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using System.Linq;
using Ninject;
using Ninject.Modules;
using Strata.Foundation.Bootstrap;
using Strata.Foundation.Logging;
using Strata.Ninject.Injection;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class NinjectApplicationFactory:
        AbstractApplicationFactory<INinjectModule,IKernel>,
        INinjectApplicationFactory
    {
        public override void
        BindLogger(INinjectModule module)
        {
            AbstractModule.SetDefaultScope(
                module
                    .Kernel
                    .Bind<ILogger>()
                    .To<NullLogger>());
        }

        public override void
        BindStartStopController(INinjectModule module)
        {
            AbstractModule.SetDefaultScope(
                module
                    .Kernel
                    .Bind<IStartStopController>()
                    .To<NullStartStopController>());
        }

        protected override INinjectModule 
        CreateBootstrapModule()
        {
            return new NinjectBootstrapModule(this);
        }

        protected override IKernel 
        CreateContainer(IList<INinjectModule> modules)
        {
            return new StandardKernel(modules.ToArray());
        }
    }
}

//  ##########################################################################
