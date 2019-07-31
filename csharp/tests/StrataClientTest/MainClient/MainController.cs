//  ##########################################################################
//  # File Name: MainController.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Windows;
using System.Windows.Input;
using Strata.Client.Controller;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Prism.Commands;

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
    class MainController:
        AbstractController<IMainCommandProvider,IMainViewModel,IMainModel>,
        IMainController
    {
        public IStartupShutdownController StartupShutdown { get;private set;}

        public ICommand                   HelpCommand { get; private set; }
        public ICommand                   SaveCommand { get; private set; }
        public ICommand                   ExitCommand { get; private set; }

        public ICommand                   CutCommand { get; private set; }
        public ICommand                   CopyCommand { get; private set; }
        public ICommand                   PasteCommand { get; private set; }

        public ICommand                   PrintCommand { get; private set; }

        public
        MainController(IMainViewModel viewModel,IStartupShutdownController startupShutdown):
            base()
        {
            StartupShutdown = startupShutdown;

            HelpCommand  = new DelegateCommand( Help );
            SaveCommand  = new DelegateCommand( Save );
            ExitCommand  = new DelegateCommand( Exit );

            CutCommand  = new DelegateCommand( Cut );
            CopyCommand  = new DelegateCommand( Copy );
            PasteCommand = new DelegateCommand( Paste );

            PrintCommand = new DelegateCommand( Print );

            ViewModel = viewModel;
            ViewModel.CommandProvider = this;
        }

        public override void
        Start()
        {
            if (Application.Current.MainWindow != null)
                Application.Current.MainWindow.Show();
        }

        public override void
        Stop()
        {
            if ( Application.Current != null )
                Application.Current.Shutdown();
        }

        protected override void
        ProcessPropertyChanged(Object sender,PropertyChangedEventArgs args)
        {
            
        }

        protected void 
        Help()
        {     
            MessageBox.Show( "Help was clicked." );
        }

        protected void 
        Save()
        {     
            MessageBox.Show( "Save was clicked." );
        }

        protected void 
        Print()
        {
           MessageBox.Show( "Print was clicked." );
        }

        protected void 
        Exit()
        {
            StartupShutdown.ShutDown();
        }

        protected void 
        Copy()
        {
           MessageBox.Show( "Copy was clicked." );
        }

        protected void 
        Paste()
        {
           MessageBox.Show( "Paste was clicked." );
        }

        protected void 
        Cut()
        {
           MessageBox.Show( "Cut was clicked." );
        }

    }
}

//  ##########################################################################
