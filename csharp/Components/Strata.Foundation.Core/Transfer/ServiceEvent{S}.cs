//  ##########################################################################
//  # File Name: ServiceEvent.cs
//  # Copyright: 2019, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Transfer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class ServiceEvent<S>
    {
        public Guid     EventId { get; protected set; }
        public Guid     CorrelationId { get; set; }
        public string   EventName { get; protected set; }
        public DateTime Timestamp { get; set; }
        public S        Source { get; set; }

        protected
        ServiceEvent(string eventName):
            this(eventName,default(S)) {}

        protected 
        ServiceEvent(string eventName,S source)
        {
            EventId = Guid.NewGuid();
            CorrelationId = Guid.Empty;
            EventName = eventName;
            Timestamp = DateTime.Now;
            Source = source;
        }
    }
}

//  ##########################################################################
