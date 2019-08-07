using System;
using System.Diagnostics;
using System.Reflection;
using System.Threading;

namespace Strata.Common.Utility
{
    /// <summary>
    /// A class providing Threading related utilities.
    /// </summary>
    public class ThreadTools
    {
        /// <summary>
        /// A 'safe' method to set the <see cref="Thread"/>.CurrentThread.Name property.  If the CurrentThread has already been named, this method
        /// does nothing.
        /// </summary>
        /// <remarks>
        /// In the framework, any <see cref="Thread"/>'s .Name property may only be set once.  Any subsequent attempts to set the
        /// name will result in an exception.  This method merely saves the extra typing involved in checking IsNullOrEmpty on 
        /// the Name property before trying to set it.
        /// </remarks>
        /// <param name="name">The name to call the thread, if possible.</param>
        public static void SetThreadName(string name)
        {
            if (string.IsNullOrEmpty(Thread.CurrentThread.Name))
            {
                Thread.CurrentThread.Name = name;
            }
        }

        /// <summary>
        /// Uses the <see cref="StackFrame"/> to determine the <see cref="MethodBase"/> of the method that called the method
        /// in which this method is run.
        /// </summary>
        /// <returns>A <see cref="MethodBase"/> representing the calling method.</returns>
        public static MethodBase GetCallingMethod()
        {
            return new StackFrame(2, false).GetMethod();
        }

        /// <summary>
        /// Uses the <see cref="StackFrame"/> to determine the <see cref="Type"/> of the class that called the method
        /// in which this method is run.
        /// </summary>
        /// <returns>The <see cref="Type"/> that called the method.</returns>
        public static Type GetCallingType()
        {
            return new StackFrame(2, false).GetMethod().DeclaringType;
        }
    }
}
