//  ##########################################################################
//  # File Name: TaskExtension.cs
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
    class TaskExtension
    {
        public static Task<U> 
        ThenApply<T,U>(this Task<T> source,Func<T,U> transform)
        {
            return 
                source.ContinueWith(
                    t => transform(t.Result));
        }

        public static Task<U>
        ThenCompose<T,U>(this Task<T> source,Func<T,Task<U>> transform)
        {
            return source.ContinueWith(t => transform(t.Result)).Unwrap();
        }

        public static Task
        ThenConsume<T>(this Task<T> source,Action<T> consumer)
        {
            return source.ContinueWith(t => consumer(t.Result));
        }


    }
}

//  ##########################################################################
