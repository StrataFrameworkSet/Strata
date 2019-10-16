//  ##########################################################################
//  # File Name: IActionQueue.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Action
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    interface IActionQueue
    {
        IActionQueue
        Register(IAction setup,IAction teardown);

        IActionQueue
        SetUp();

        IActionQueue
        TearDown();

        IActionQueue
        Insert(IAction action);

        IAction
        Remove();

        IActionQueue
        Clear();

        IActionQueue
        Execute();

        bool
        IsEmpty();
    }
}

//  ##########################################################################
