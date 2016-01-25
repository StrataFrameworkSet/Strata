//  ##########################################################################
//  # File Name: IMainController.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Client.Controller;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMainController:
        IMainCommandProvider,
        IController<IMainCommandProvider,IMainViewModel,IMainModel> {}
}

//  ##########################################################################
