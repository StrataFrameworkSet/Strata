//  ##########################################################################
//  # File Name: LoggingInterceptor.cs
//  ##########################################################################

using Ninject.Extensions.Interception;
using Strata.Ninject.Interception;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;

namespace Strata.Application.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class LoggingInterceptor:
        Interceptor
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected
        LoggingInterceptor() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void 
        DoBefore(IInvocation invocation)
        {
            LogInfo("Begin: " + GetSignature(invocation));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void 
        DoAfter(IInvocation invocation)
        {
            LogInfo("End: " + GetSignature(invocation));
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        protected override void 
        OnException(IInvocation invocation,Exception e)
        {
            LogWarning(e.GetType().Name + ": " + e.Message,e);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected string
        GetSignature(IInvocation invocation)
        {
            StringBuilder builder = new StringBuilder();
            MethodInfo    method  = invocation.Request.Method;

            builder
                .Append(method.Name)
                .Append('(')
                .Append(GetParameterTypeNames(method.GetParameters()))
                .Append(')');

            return builder.ToString();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected string
        GetParameterTypeNames(ParameterInfo[] parameters)
        {
            StringBuilder             builder = new StringBuilder();
            LinkedList<ParameterInfo> temp = 
                new LinkedList<ParameterInfo>(parameters);

            if (temp.Count > 0)
            {
                ParameterInfo parameter = temp.First();

                builder.Append(parameter.ParameterType.Name);
                temp.RemoveFirst();
            }

            foreach (ParameterInfo parameter in temp)
                builder
                    .Append(',')
                    .Append(parameter.ParameterType.Name);

            return builder.ToString();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected abstract void
        LogInfo(string message);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected abstract void
        LogWarning(string message,Exception e);
    }
}

//  ##########################################################################
