//  ##########################################################################
//  # File Name: MessageViewModel.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows.Input;
using Strata.Client.Command;
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
    class MessageViewModel:
        AbstractViewModel<INullCommandProvider>,
        IMessageViewModel
    {
        public String Message { get;set; }
        public bool   Visible {get;set; }

        public
        MessageViewModel()
        {
            Message = String.Empty;
            Visible = false;
        }

    }
}

//  ##########################################################################
