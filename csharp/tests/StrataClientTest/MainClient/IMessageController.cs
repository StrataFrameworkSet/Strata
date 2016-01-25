//  ##########################################################################
//  # File Name: IMessageController.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Client.Command;
using Strata.Client.Controller;
using Strata.Client.ViewModel;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    interface IMessageController:
        INullCommandProvider,
        IController<INullCommandProvider,IMessageViewModel,IMainModel>
    {
        void
        DisplayMessage(String message);
    }
}

//  ##########################################################################
