//  ##########################################################################
//  # File Name: IMainViewModel.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using System.Windows.Input;
using Strata.Client.ViewModel;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMainViewModel:
        IMainCommandProvider,
        IViewModel<IMainCommandProvider> {}
}

//  ##########################################################################
