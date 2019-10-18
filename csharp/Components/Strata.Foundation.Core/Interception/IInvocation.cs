//  ##########################################################################
//  # File Name: IInvocation.cs
//  ##########################################################################

using System.Collections.Generic;
using System.Reflection;

namespace Strata.Foundation.Core.Interception
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    interface IInvocation
    {
        object        Target { get;  }
        MethodInfo    Method { get; }
        IList<object> Arguments { get;  }
        object        ReturnValue { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        IInvocationResult
        Proceed();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        T
        GetArgumentAsType<T>(string name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        bool
        HasArgumentOfType<T>(string name);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        bool
        HasReturnOfType<T>();
    }
}

//  ##########################################################################
