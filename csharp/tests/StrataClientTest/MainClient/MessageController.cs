//  ##########################################################################
//  # File Name: MessageController.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Input;
using Strata.Client.Command;
using Strata.Client.Controller;
using Strata.Client.ViewModel;
using Microsoft.Practices.Prism.Commands;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class MessageController:
        AbstractController<INullCommandProvider,IMessageViewModel,IMainModel>,
        IMessageController
    {

        public
        MessageController(IMessageViewModel viewModel):
            base()
        {
            ViewModel = viewModel;
            ViewModel.CommandProvider = this;
        }

        public override void
        Start() {}

        public override void
        Stop() {}

        public void 
        DisplayMessage(String message)
        {
            ViewModel.Message = message;
            ViewModel.Visible = true;
        }

        protected override void
        ProcessPropertyChanged(Object sender,PropertyChangedEventArgs args)
        {
        }
    }
}

//  ##########################################################################
