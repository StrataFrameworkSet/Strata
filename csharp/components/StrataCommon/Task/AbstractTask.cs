//  ##########################################################################
//  # File Name: AbstractTask.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractTask:
        ITask
    {
        public string Name { get; private set; }

        private IDictionary<String,Object> properties;
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>AbstractTask</c> instance.
        /// </summary>
        /// 
        /// <param name="name">task name</param>
        /// 
        protected
        AbstractTask(String name)
        {
            Name       = name;
            properties = new ConcurrentDictionary<String,Object>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public ITask 
        SetProperty<T>(String key,T value) 
        {
            properties[key] = value;
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public T 
        GetProperty<T>(String key) 
         {
            if ( HasProperty<T>( key ) )
                return (T)properties[key];

            throw new KeyNotFoundException(key); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public bool 
        HasProperty<T>(String key) 
        {
            return 
                properties.ContainsKey( key ) &&
                properties[key] is T;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public abstract void
        Execute();
    }
}

//  ##########################################################################
