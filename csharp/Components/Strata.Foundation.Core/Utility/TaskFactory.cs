
//  ##########################################################################
//  # File Name: TaskFactory.cs
//  ##########################################################################

using System;
using System.Threading.Tasks;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public static
    class TaskFactory
    {
        public static Task<T>
        SupplyAsyc<T>(Func<T> supplier)
        {
            Task<T> task = new Task<T>(supplier);
            
            task.Start();
            return task;
        }
    }
}

//  ##########################################################################
