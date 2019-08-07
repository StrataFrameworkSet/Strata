using System;

namespace Strata.Common.ExceptionManagement
{
    ///<summary>
    ///</summary>
    public interface IExceptionManager
    {
        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="defaultResult"></param>
        ///<param name="policyName"></param>
        ///<typeparam name="TResult"></typeparam>
        ///<returns></returns>
        TResult Process<TResult>(Func<TResult> action, TResult defaultResult, string policyName);

        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="policyName"></param>
        ///<typeparam name="TResult"></typeparam>
        ///<returns></returns>
        TResult Process<TResult>(Func<TResult> action, string policyName);

        ///<summary>
        ///</summary>
        ///<param name="action"></param>
        ///<param name="policyName"></param>
        void Process(Action action, string policyName);
    }
}
