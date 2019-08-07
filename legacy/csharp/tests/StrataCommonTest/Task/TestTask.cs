//  ##########################################################################
//  # File Name: TestTask.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using Strata.Common.Logging;
using Strata.Common.Utility;

namespace Strata.Common.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class TestTask:
        AbstractTask
    {
        public ILogger    Logger { get; set; }
        public AtomicLong Counter { get; set; }

        public
        TestTask(String name,int taskId,ILogger logger,AtomicLong counter):
            base( name )
        {
            SetProperty( "TaskId",taskId );
            Logger = logger;
            Counter = counter;
        }

        public override void
        Execute()
        {
	        StringBuilder builder = new StringBuilder();
	        
	        builder
	            .Append( "Execute()\n" )
	            .Append( "      name = " )
	            .Append( Name ).Append( "\n" )
	            .Append( "    taskId = ")
	            .Append( GetProperty<int>("TaskId")).Append( "\n" )
	            .Append( "    thread = " )
	            .Append( Thread.CurrentThread.ManagedThreadId ).Append( "\n" );
	        
	        Logger.Info( builder.ToString() );
            Counter.IncrementAndGet();
        }
    }
}

//  ##########################################################################
