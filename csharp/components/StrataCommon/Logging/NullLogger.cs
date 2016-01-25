//  ##########################################################################
//  # File Name: NullLogger.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.Logging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A <a href="http://en.wikipedia.org/wiki/Null_Object_pattern">
    /// Null Object</a> that implements the <c>ILogger</c> interface. 
    /// Can be used in unit tests where logging to an external source is not
    /// desired.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class NullLogger:
        ILogger
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NullLogger</c> instance.
        /// </summary>
        /// 
        public 
        NullLogger() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Verbose(String)"/>
        /// </summary>
        /// 
        public void 
        Verbose(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Info(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Info(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Warn(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Warn(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Error(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Error(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Critical(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Critical(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Start(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Start(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Stop(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Stop(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Suspend(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Suspend(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Resume(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Resume(String message) {}
    }
}

//  ##########################################################################
