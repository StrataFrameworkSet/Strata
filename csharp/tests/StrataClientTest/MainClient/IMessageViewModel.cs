//  ##########################################################################
//  # File Name: IMessageViewModel.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Client.Command;
using Strata.Client.PropertyChange;
using Strata.Client.ViewModel;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMessageViewModel:
        INullCommandProvider,
        IViewModel<INullCommandProvider>
    {
        String Message { get;set; }
        bool   Visible { get;set; }
    }
}

//  ##########################################################################
