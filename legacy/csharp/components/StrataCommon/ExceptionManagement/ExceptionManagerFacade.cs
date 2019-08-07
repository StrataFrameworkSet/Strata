using System;
using Microsoft.Practices.EnterpriseLibrary.ExceptionHandling;
using Microsoft.Practices.Unity;

namespace Strata.Common.ExceptionManagement
{
    ///<summary>
    ///</summary>
    public class ExceptionManagerFacade : IExceptionManager
    {
        ///<summary>
        /// The injected Enterprise Library <see cref="ExceptionManager"/>.
        ///</summary>
        [Dependency]
        public ExceptionManager ExceptionManager { get; set; }

        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="defaultResult"></param>
        ///<param name="policyName"></param>
        ///<typeparam name="TResult"></typeparam>
        ///<returns></returns>
        public TResult Process<TResult>(Func<TResult> action, TResult defaultResult, string policyName)
        {
            return ExceptionManager.Process(action, defaultResult, policyName);
        }

        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="policyName"></param>
        ///<typeparam name="TResult"></typeparam>
        ///<returns></returns>
        public TResult Process<TResult>(Func<TResult> action, string policyName)
        {
            return ExceptionManager.Process(action, policyName);
        }

        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="policyName"></param>
        public void Process(Action action, string policyName)
        {
            ExceptionManager.Process(action, policyName);
        }
    }
}
