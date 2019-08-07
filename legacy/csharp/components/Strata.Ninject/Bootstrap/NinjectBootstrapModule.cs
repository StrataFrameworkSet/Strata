//  ##########################################################################
//  # File Name: NinjectBootstrapModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Modules;
using Strata.Ninject.Injection;
using System;

namespace Strata.Ninject.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectBootstrapModule:
        AbstractModule
    {
        private readonly INinjectApplicationFactory factory;

        public
        NinjectBootstrapModule(INinjectApplicationFactory f)
        {
            factory = f;
        }

        public override void 
        Load()
        {
            factory.BindLogger(this);
            factory.BindStartStopController(this);
        }
    }
}

//  ##########################################################################
