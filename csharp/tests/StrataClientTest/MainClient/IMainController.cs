//  ##########################################################################
//  # File Name: IMainController.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
