//  ##########################################################################
//  # File Name: NullCommand.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows.Input;

namespace Strata.Client.Command
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Null object for commands.
    /// </summary>
    ///  
    public 
    class NullCommand:
        ICommand
    {
        public bool CanExecute(object parameter)
        {
            return false;
        }

        public void Execute(object parameter) {}

        public event EventHandler CanExecuteChanged;
    }
}

//  ##########################################################################
