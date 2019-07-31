//  ##########################################################################
//  # File Name: Interceptor.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Extensions.Interception;
using System;

namespace Strata.Ninject.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class Interceptor:
        IInterceptor
    {
        protected
        Interceptor() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public virtual void 
        Intercept(IInvocation invocation)
        {
            DoBefore(invocation);

            try
            {
                DoProceed(invocation);
            }
            catch (Exception e)
            {
                OnException(invocation,e);
                throw;
            }
            finally
            {
                DoAfter(invocation);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void 
        DoBefore(IInvocation invocation) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void
        DoProceed(IInvocation invocation)
        {
            invocation.Proceed();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void 
        DoAfter(IInvocation invocation) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///
        protected virtual void 
        OnException(IInvocation invocation,Exception e) {}
    }
}

//  ##########################################################################
