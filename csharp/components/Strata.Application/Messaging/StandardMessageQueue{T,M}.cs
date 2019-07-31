//  ##########################################################################
//  # File Name: StandardMessageQueue{T,M}.cs
//  ##########################################################################

using System;
using System.Collections;
using System.Collections.Generic;

namespace Strata.Application.Messaging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class StandardMessageQueue<T,M>:
        IMessageQueue<T,M>
    {
        private readonly Queue<Tuple<T,M>> implementation;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        StandardMessageQueue():
            this(new Queue<Tuple<T,M>>()) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        StandardMessageQueue(Queue<Tuple<T,M>> imp)
        {
            implementation = imp;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IMessageQueue<T,M> 
        Insert(T topic,M message)
        {
            implementation.Enqueue(Tuple.Create(topic,message));
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual Tuple<T,M>
        Remove()
        {
            return implementation.Dequeue();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Clear()
        {
            implementation.Clear();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool 
        IsEmpty()
        {
            return implementation.Count == 0;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IEnumerator<Tuple<T,M>> 
        GetEnumerator()
        {
            return implementation.GetEnumerator();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        IEnumerator
        IEnumerable.GetEnumerator()
        {
            return implementation.GetEnumerator();
        }
    }
}

//  ##########################################################################
