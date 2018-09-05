//  ##########################################################################
//  # File Name: UnitOfWorkException.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Text;

namespace Strata.Domain.UnitOfWork
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class UnitOfWorkException:
        Exception
    {
        protected 
        UnitOfWorkException(String message):
            base(message) {}

        protected 
        UnitOfWorkException(String message,Exception cause):
            base(
                InitializeMessage(new StringBuilder(message),cause),
                cause) {}

        private static string
        InitializeMessage(StringBuilder message,Exception e)
        {
            message.Append(
                string.Format(
                    " (caused by {0}: {1}",
                    e.GetType().Name,e.Message));

            if (e.InnerException != null)
                InitializeMessage(message,e.InnerException);

            message.Append(')');
            return message.ToString();
        }
    }
}

//  ##########################################################################
