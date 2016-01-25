//  ##########################################################################
//  # File Name: MainViewModel.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows;
using System.Windows.Controls.Ribbon;
using System.Windows.Forms;
using System.Windows.Input;
using Strata.Client.ViewModel;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Prism.Commands;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class MainViewModel:
        AbstractViewModel<IMainCommandProvider>,
        IMainViewModel
    {
        public ICommand   
        HelpCommand { get { return CommandProvider.HelpCommand; } }

        public ICommand   
        SaveCommand { get { return CommandProvider.SaveCommand; } }

        public ICommand   
        ExitCommand { get { return CommandProvider.ExitCommand; } }

        public ICommand   
        CutCommand { get { return CommandProvider.CutCommand; } }

        public ICommand   
        CopyCommand { get { return CommandProvider.CopyCommand; } }

        public ICommand   
        PasteCommand { get { return CommandProvider.PasteCommand; } }

        public ICommand   
        PrintCommand { get { return CommandProvider.PrintCommand; } }

        public 
        MainViewModel():
            base() {}

    }
}

//  ##########################################################################
