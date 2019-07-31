//  ##########################################################################
//  # File Name: ConsoleLoggingInterceptor.cs
//  ##########################################################################

using System;
using System.Text;

namespace Strata.Application.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class ConsoleLoggingInterceptor:
        LoggingInterceptor
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        ConsoleLoggingInterceptor() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void 
        LogInfo(string message)
        {
            StringBuilder builder = new StringBuilder();

            builder
                .AppendLine()
                .Append(GenerateHeader("INFO:"))
                .AppendLine()
                .Append(message)
                .AppendLine();

            Console
                .Out
                .WriteLine(builder.ToString());
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void 
        LogWarning(string message,Exception e)
        {
            StringBuilder builder = new StringBuilder();

            builder
                .AppendLine()
                .Append(GenerateHeader("WARNING:"))
                .AppendLine()
                .Append(message)
                .Append(' ')
                .Append(e.StackTrace)
                .AppendLine();

            Console
                .Out
                .WriteLine(builder.ToString());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected virtual string
        GenerateHeader(string kind)
        {
            StringBuilder builder = new StringBuilder();

            builder
                .Append(DateTime.Now)
                .Append(' ')
                .Append(kind);

            return builder.ToString();
        }
    }
}

//  ##########################################################################
