//  ##########################################################################
//  # File Name: Decorator.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Castle.DynamicProxy;
using System;

namespace Strata.Foundation.Decoration
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class Decorator:
        IInterceptor
    {
        protected
        Decorator() {}

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
