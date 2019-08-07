//  ##########################################################################
//  # File Name: TaskEvent.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents a task request event that can be 
    /// </summary>
    ///  
    public 
    class TaskEvent
    {
        public ITask        Task { get; set; }
        public DispatchKind Kind { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>TaskEvent</c> instance.
        /// </summary>
        /// 
        public 
        TaskEvent()
        {
            Task = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates new <c>TaskEvent</c> instance.
        /// </summary>
        /// 
        public static TaskEvent
        createTaskEvent()
        {
            return new TaskEvent();
        }
    }
}

//  ##########################################################################
