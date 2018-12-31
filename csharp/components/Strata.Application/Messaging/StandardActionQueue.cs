//  ##########################################################################
//  # File Name: StandardActionQueue.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Application.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Standard implementation of <c>IActionQueue</c> interface.
    /// </summary>
    ///  
    public
    class StandardActionQueue:
        IActionQueue
    {
        private readonly Queue<Action> actions;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new instance of <c>StandardActionQueue</c>.
        /// </summary>
        ///
        public
        StandardActionQueue():
            this(new Queue<Action>()) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new instance of <c>StandardActionQueue</c> 
        /// with the specified queue.
        /// </summary>
        ///
        public
        StandardActionQueue(Queue<Action> a)
        {
            actions = a;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public IActionQueue 
        Insert(Action action)
        {
            actions.Enqueue(action);
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public Action 
        Remove()
        {
            return actions.Dequeue();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public void 
        Clear()
        {
            actions.Clear();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public bool 
        IsEmpty()
        {
            return actions.Count == 0;
        }
    }
}

//  ##########################################################################
