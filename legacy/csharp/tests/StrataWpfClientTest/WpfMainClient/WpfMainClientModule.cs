//  ##########################################################################
//  # File Name: WpfMainClientModule.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Client.MainClient;
using Microsoft.Practices.Unity;

namespace Strata.WpfClient.WpfMainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class WpfMainClientModule:
        MainClientModule
    {
        public 
        WpfMainClientModule(): 
            base( "WpfMainClientModule" ) {}

        public override void 
        Initialize(IUnityContainer container)
        {
            WpfMainView    view      = null;
            IMainViewModel viewModel = null;

            base.Initialize( container );
            view = (WpfMainView)container.Resolve<IMainView>();
            viewModel = container.Resolve<IMainViewModel>();
            view.DataContext = viewModel;
        }
    }
}

//  ##########################################################################
