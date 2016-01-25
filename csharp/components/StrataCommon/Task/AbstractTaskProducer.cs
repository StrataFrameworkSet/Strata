//  ##########################################################################
//  # File Name: AbstractTaskProducer.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
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
    class AbstractTaskProducer:
        ITaskProducer
    {
        public ITaskDispatcher Dispatcher { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>AbstractTaskProducer</c> instance.
        /// </summary>
        /// 
        protected
        AbstractTaskProducer()
        {
            Dispatcher = null;    
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public abstract void 
        StartProducing();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public abstract void 
        StopProducing();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// </summary>
        /// 
        public abstract bool 
        IsProducing();
    }
}

//  ##########################################################################
