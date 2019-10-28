//  ##########################################################################
//  # File Name: TaskExtension.cs
//  ##########################################################################

using System;
using System.Linq;
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

        public static void 
        SetFromTask<T>(this TaskCompletionSource<T> completion,Task task)
        {
            switch (task.Status)
            {
                case TaskStatus.RanToCompletion: 
                    completion.SetResult(task is Task<T> ? ((Task<T>)task).Result : default(T)); 
                    break;
                case TaskStatus.Faulted: 
                    completion.SetException(task.Exception.InnerExceptions); 
                    break;
                case TaskStatus.Canceled: 
                    completion.SetCanceled(); 
                    break;
                default: 
                    throw new InvalidOperationException("The task was not completed.");
            }
        }
        
        public static void 
        SetFromTask<T>(this TaskCompletionSource<T> completion,Task<T> task)
        {
            SetFromTask(completion,(Task)task);
        }

        public static void 
        WaitNoThrow(this Task task)
        {
            try
            {
                if (task.IsCanceled == false)
                {
                    task.Wait();
                }
            }
            catch (AggregateException errors)
            {
                errors.Handle(e => e != null);
            }
        }

        public static Task<U> 
        ThenApply<T,U>(this Task<T> source,Func<T,U> transform)
        {
            return
                source
                    .ContinueWith(
                        t =>
                        {
                            if (t.IsFaulted||t.IsCanceled)
                                return default(U);

                            return transform(t.Result);
                        },
                        TaskContinuationOptions.ExecuteSynchronously);
        }

        public static Task<U>
        ThenCompose<T,U>(this Task<T> source,Func<T,Task<U>> transform)
        {
            return
                source
                    .ContinueWith(
                        t =>
                        {
                            if (t.IsFaulted||t.IsCanceled)
                                return Task.FromException<U>(t.Exception.InnerExceptions.First());

                            return transform(t.Result);
                        },
                        TaskContinuationOptions.ExecuteSynchronously)
                    .Unwrap();
        }

        public static Task
        ThenConsume<T>(this Task<T> source,Action<T> consumer)
        {
            return
                source
                .ContinueWith(
                    t =>
                    {
                        if (t.IsCompletedSuccessfully)
                            consumer(t.Result);
                    },
                    TaskContinuationOptions.ExecuteSynchronously);
        }


        public static Task<T>
        WhenException<T>(this Task<T> source,Func<AggregateException,T> handle)
        {
            return 
                source.ContinueWith(
                    t =>
                    {
                        if (t.IsCompletedSuccessfully)
                            return t.Result;

                        return handle(t.Exception);
                    },
                    TaskContinuationOptions.ExecuteSynchronously);
        }

    }
}

//  ##########################################################################
