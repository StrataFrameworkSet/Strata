//  ##########################################################################
//  # File Name: CastleLoggingInterceptor.cs
//  ##########################################################################

using Castle.Core.Logging;
using System;

namespace Strata.Application.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class CastleLoggingInterceptor:
        LoggingInterceptor
    {
        public ILogger Logger { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        CastleLoggingInterceptor(ILogger logger)
        {
            Logger = logger;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void
        LogInfo(string message)
        {
            Logger.Info(message);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void
        LogWarning(string message,Exception e)
        {
            Logger.Warn(message,e);
        }
    }
}

//  ##########################################################################
