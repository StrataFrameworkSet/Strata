//  ##########################################################################
//  # File Name: IMainCommandProvider.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Windows.Input;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMainCommandProvider
    {
        ICommand HelpCommand { get; }
        ICommand SaveCommand { get; }
        ICommand ExitCommand { get; }

        ICommand CutCommand { get; }
        ICommand CopyCommand { get; }
        ICommand PasteCommand { get; }

        ICommand PrintCommand { get; }
    }
}

//  ##########################################################################
