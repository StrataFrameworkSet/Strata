//  ##########################################################################
//  # File Name: NullLogger.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Logging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A <a href="http://en.wikipedia.org/wiki/Null_Object_pattern">
    /// Null Object</a> that implements the <c>ILogger</c> interface. 
    /// Can be used in unit tests where logging to an external source is not
    /// desired.
    /// </summary>
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
        /// <inheritDoc/>
        /// 
        public void 
        Verbose(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Info(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Warn(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Error(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Critical(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Start(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Stop(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Suspend(String message) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Resume(String message) {}
    }
}

//  ##########################################################################
