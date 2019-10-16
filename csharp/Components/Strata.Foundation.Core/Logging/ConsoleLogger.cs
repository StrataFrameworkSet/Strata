//  ##########################################################################
//  # File Name: ConsoleLogger.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Foundation.Core.Logging
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A <c>Console</c>-based logger that implements the <c>ILogger</c> 
    /// interface. Can be used in unit tests where logging to an external 
    /// source is not desired.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class ConsoleLogger:
        ILogger
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>NullLogger</c> instance.
        /// </summary>
        /// 
        public 
        ConsoleLogger() {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Verbose(String)"/>
        /// </summary>
        /// 
        public void 
        Verbose(String message)
        {
            WriteToOut( "Verbose",message );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Info(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Info(String message)
        {
            WriteToOut( "Info",message );            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Warn(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Warn(String message)
        {
            WriteToOut( "Warn",message );                        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Error(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Error(String message)
        {
            WriteToError( "Error",message );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Critical(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Critical(String message)
        {
            WriteToError( "Critical",message );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Start(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Start(String message)
        {
            WriteToOut( "Start",message );                        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Stop(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Stop(String message)
        {
            WriteToOut( "Stop",message );                        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Suspend(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Suspend(String message)
        {
            WriteToOut( "Suspend",message );                        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="ILogger.Resume(String)"/>
        /// Null Object: this method does nothing.
        /// </summary>
        /// 
        public void 
        Resume(String message)
        {
            WriteToOut( "Resume",message );                        
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private void
        WriteToOut(String level,String message)
        {
            Console.Out.WriteLine(DateTime.Now.ToLongDateString() + " " + level );
            Console.Out.WriteLine(message);
            Console.Out.WriteLine();            
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        private void
        WriteToError(String level,String message)
        {
            Console.Error.WriteLine(DateTime.Now.ToLongDateString() + " " + level );
            Console.Error.WriteLine(message);
            Console.Error.WriteLine();            
        }
    }
}

//  ##########################################################################
