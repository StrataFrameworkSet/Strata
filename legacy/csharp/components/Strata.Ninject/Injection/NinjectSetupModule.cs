//  ##########################################################################
//  # File Name: NinjectSetupModule.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Modules;
using Strata.Foundation.Injection;
using System;

namespace Strata.Ninject.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectSetupModule:
        NinjectModule
    {
        private IContainer itsContainer;

        public
        NinjectSetupModule(IContainer container)
        {
            itsContainer = container;
        }

        public override void 
        Load()
        {
            Bind<IContainer>().ToConstant<IContainer>(itsContainer);
        }
    }
}

//  ##########################################################################
