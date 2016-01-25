//  ##########################################################################
//  # File Name: IMainView.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
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
