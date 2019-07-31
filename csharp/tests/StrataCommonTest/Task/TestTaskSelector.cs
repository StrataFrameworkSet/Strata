//  ##########################################################################
//  # File Name: TestTaskSelector.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public class TestTaskSelector:
        ITaskSelector,
        IEquatable<ITaskSelector>
    {
        private int taskId;

        public
        TestTaskSelector(int tid)
        {
            taskId = tid;
        }

        public bool 
        Match(ITask task)
        {
            if ( task.HasProperty<int>( "TaskId" ) )
	            return task.GetProperty<int>( "TaskId" ) == taskId;
	            
	        return false;
        }

        public bool 
        Equals(ITaskSelector other)
        {
            if ( other is TestTaskSelector )
                return taskId == ((TestTaskSelector)other).taskId;

            return false;
        }

        public override int
        GetHashCode()
        {
            return 37 * taskId.GetHashCode(); 
        }
        
    }
}

//  ##########################################################################
