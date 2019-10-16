//  ##########################################################################
//  # File Name: NlogLogger.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using NLog;

namespace Strata.Foundation.Core.Logging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NlogLogger:
        ILogger
    {
        private Logger itsImp;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        NlogLogger(Logger imp)
        {
            itsImp = imp;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Verbose(String message)
        {
            itsImp.Debug(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Info(String message)
        {
            itsImp.Info(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Warn(String message)
        {
            itsImp.Warn(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Error(String message)
        {
            itsImp.Error(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Critical(String message)
        {
            itsImp.Fatal(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Start(String message)
        {
            itsImp.Debug(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Stop(String message)
        {
            itsImp.Debug(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Suspend(String message)
        {
            itsImp.Debug(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Resume(String message)
        {
            itsImp.Debug(message);
        }
    }
}

//  ##########################################################################
