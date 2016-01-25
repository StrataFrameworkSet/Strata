//  ##########################################################################
//  # File Name: AbstractTaskDispatcher.cs
//  # Copyright: 2014, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Utility;

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
    class AbstractTaskDispatcher:
        ITaskDispatcher
    {
        public IExceptionHandler ExceptionHandler { get; set; }

        private IMultiMap<ITaskSelector,ITaskConsumer> consumers;

        protected
        AbstractTaskDispatcher()
        {
            consumers = new MultiMap<ITaskSelector,ITaskConsumer>();    
        }

        public ITaskDispatcher 
        AttachConsumer(ITaskConsumer consumer)
        {
            consumers.Insert( consumer.Selector,consumer );
            return this;
        }

        public ITaskDispatcher 
        DetachConsumer(ITaskConsumer consumer)
        {
            consumers.Remove( consumer.Selector,consumer );
            return this;
        }

        public abstract void 
        Route(ITask task);

        public abstract void 
        Broadcast(ITask task);

        public bool 
        HasConsumer(ITaskConsumer consumer)
        {
            return consumers.ContainsValue( consumer.Selector,consumer );
        }

        public abstract void 
        StartDispatching();

        public abstract void 
        StopDispatching();

        public abstract bool 
        IsDispatching();

        protected IMultiMap<ITaskSelector,ITaskConsumer>
        GetConsumers()
        {
            return consumers;
        }
    }
}

//  ##########################################################################
