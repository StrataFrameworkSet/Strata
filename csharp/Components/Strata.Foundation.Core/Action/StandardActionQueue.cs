//  ##########################################################################
//  # File Name: StandardActionQueue.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Action
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class StandardActionQueue:
        IActionQueue
    {
        private readonly Queue<IAction> itsSetups;
        private readonly Queue<IAction> itsTeardowns;
        private readonly Queue<IAction> itsActions;

        public
        StandardActionQueue()
        {
            itsSetups = new Queue<IAction>();
            itsTeardowns = new Queue<IAction>();
            itsActions = new Queue<IAction>();
        }

        public IActionQueue 
        Register(IAction setup,IAction teardown)
        {
            itsSetups.Enqueue(setup);
            itsTeardowns.Enqueue(teardown);
            return this;
        }

        public IActionQueue 
        SetUp()
        {
            foreach (IAction setup in itsSetups)
                setup.Invoke();

            return this;
        }

        public IActionQueue 
        TearDown()
        {
            foreach (IAction teardown in itsTeardowns)
                teardown.Invoke();

            return this;
        }

        public IActionQueue 
        Insert(IAction action)
        {
            itsActions.Enqueue(action);
            return this;
        }

        public IAction 
        Remove()
        {
            return itsActions.Dequeue();
        }

        public IActionQueue 
        Clear()
        {
            itsSetups.Clear();
            itsTeardowns.Clear();
            itsActions.Clear();
            return this;
        }

        public IActionQueue 
        Execute()
        {
            try
            {
                SetUp();

                while (itsActions.Count != 0)
                    itsActions
                        .Dequeue()
                        .Invoke();

                return this;
            }
            finally
            {
                TearDown();
            }
        }

        public bool 
        IsEmpty()
        {
            return itsActions.Count == 0;
        }
    }
}

//  ##########################################################################
