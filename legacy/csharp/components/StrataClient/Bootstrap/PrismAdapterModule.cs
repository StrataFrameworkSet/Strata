//  ##########################################################################
//  # File Name: PrismAdapterModule.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Unity;

namespace Strata.Client.Bootstrap
{
    using IPrismModule = Microsoft.Practices.Prism.Modularity.IModule;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class PrismAdapterModule:
        IPrismModule
    {
        private IUnityContainer container;
        private IList<IModule>  modules; 

        public
        PrismAdapterModule(IUnityContainer c,IList<IModule>  m)
        {
            container = c;
            modules   = m;
        }

        public void
        Initialize()
        {
            foreach (IModule module in modules)
                module.Initialize( container );
        }
    }
}

//  ##########################################################################
