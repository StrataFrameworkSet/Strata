//  ##########################################################################
//  # File Name: IActionQueue.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Application.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A queue of <c>Action</c>s that are executed after a 
    /// <c>IUnitOfWork</c> has been successfully committed.
    /// </summary>
    ///  
    public
    interface IActionQueue
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        IActionQueue
        Insert(Action action);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        Action
        Remove();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        void
        Clear();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        bool
        IsEmpty();
    }
}

//  ##########################################################################
