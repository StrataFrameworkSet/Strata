//  ##########################################################################
//  # File Name: AbstractInterceptor.cs
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractInterceptor :
        IInterceptor
    {
        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///
        public virtual IInvocationResult
        Intercept(IInvocation invocation)
        {
            DoBefore(invocation);

            try
            {
                return DoProceed(invocation);
            }
            catch (Exception e)
            {
                OnException(invocation, e);
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
        protected IInvocationResult
        DoProceed(IInvocation invocation)
        {
            return invocation.Proceed();
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
        OnException(IInvocation invocation, Exception e) {}
    }
}

//  ##########################################################################
