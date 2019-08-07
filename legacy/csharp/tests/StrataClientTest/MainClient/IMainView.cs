//  ##########################################################################
//  # File Name: IMainView.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMainView
    {
        IMainViewModel ViewModel { get;set; }
    }
}

//  ##########################################################################
