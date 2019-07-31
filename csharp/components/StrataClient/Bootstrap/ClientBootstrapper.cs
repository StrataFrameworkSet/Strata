//  ##########################################################################
//  # File Name: ClientBootstrapper.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Bootstrap;

namespace Strata.Client.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class ClientBootstrapper:
        Bootstrapper
    {
        private PrismBootstrapHelper prism;

        public
        ClientBootstrapper()
        {
            prism   = null;
        }

        public override void 
        Run(IBootstrapFactory factory)
        {
            prism = 
                new PrismBootstrapHelper( 
                    this,
                    (IClientBootstrapFactory)factory );
            base.Run( factory );
        }

        protected override void 
        InitializeModules()
        {
           prism.Run(false);
        }
    }
}

//  ##########################################################################
