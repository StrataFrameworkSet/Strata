//  ##########################################################################
//  # File Name: ITask.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Task
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Represents an executable task.
    /// </summary>
    ///  
    public 
    interface ITask
    {
        String Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets a property on the <c>ITask</c> using the specified 
        /// key and value
        /// </summary>
        /// 
        /// <typeparam name="T">the value type of the property</typeparam>
        /// 
        /// <param name="key">property key</param>
        /// <param name="value">property value</param>
        /// 
        /// <returns>this task to enable method chaining</returns>
        /// 
        ITask
        SetProperty<T>(String key,T value);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets a property on the <c>ITask</c> 
        /// identified by the specified key.
        /// </summary>
        /// 
        /// <typeparam name="T">the value type of the property</typeparam>
        /// 
        /// <param name="key">property key</param>
        /// 
        /// <returns>property value</returns>
        /// 
        T
        GetProperty<T>(String key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Determines if an <c>ITask</c> object has the property 
        /// identified by the specified key.
        /// </summary>
        /// 
        /// <typeparam name="T">the value type of the property</typeparam>
        /// 
        /// <param name="key">property key</param>
        /// 
        /// <returns>
        /// true if <c>ITask</c> object has the specified property
        /// </returns>
        /// 
        bool
        HasProperty<T>(String key);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes the <c>ITask</c>.
        /// </summary>
        /// 
        void
        Execute();
    }
}

//  ##########################################################################
