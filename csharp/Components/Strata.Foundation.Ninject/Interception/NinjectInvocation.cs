//  ##########################################################################
//  # File Name: NinjectInvocation.cs
//  ##########################################################################

using System.Collections.Concurrent;
using System.Linq;
using System.Threading.Tasks;
using Strata.Foundation.Core.Interception;

namespace Strata.Foundation.Ninject.Interception
{
    using System.Collections.Generic;
    using System.Reflection;
    using INinjectInvocation =
        global::Ninject.Extensions.Interception.IInvocation;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class NinjectInvocation:
        IInvocation
    {
        public object        
        Target { get { return Adaptee.Request.Target; } }

        public MethodInfo    
        Method { get { return Adaptee.Request.Method; } }

        public IList<object> 
        Arguments { get { return Adaptee.Request.Arguments.ToList(); } }

        public object        
        ReturnValue
        {
            get { return Adaptee.ReturnValue; }
            set { Adaptee.ReturnValue = value; }
        }

        public INinjectInvocation Adaptee { get; protected set; }

        private readonly IDictionary<string,object> itsArgumentMap;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        NinjectInvocation(INinjectInvocation adaptee)
        {
            Adaptee = adaptee;
            itsArgumentMap = InitializeArgumentsMap(Adaptee);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IInvocationResult 
        Proceed()
        {
            Adaptee.Proceed();

            return new NinjectInvocationResult(Adaptee.ReturnValue);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual T 
        GetArgumentAsType<T>(string name)
        {
            if (itsArgumentMap.ContainsKey(name))
            {
                object argument = itsArgumentMap[name];

                if (argument is T)
                    return (T)argument;
            }

            return default(T);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        HasArgumentOfType<T>(string name)
        {
            if (itsArgumentMap.ContainsKey(name))
                return itsArgumentMap[name] is T;
            
            return false;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        HasReturnOfType<T>()
        {
            return
                typeof(T).IsAssignableFrom(
                    Adaptee
                        .Request
                        .Method
                        .ReturnType);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected IDictionary<string,object>
        InitializeArgumentsMap(INinjectInvocation adaptee)
        {
            IDictionary<string,object> output =
                new ConcurrentDictionary<string,object>();
            ParameterInfo[] parameters =
                adaptee
                    .Request
                    .Method
                    .GetParameters();
            object[] arguments =
                adaptee
                    .Request
                    .Arguments;

            int i = 0;

            foreach (ParameterInfo parameter in parameters)
                output[parameter.Name] = arguments[i++];


            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected static async Task
        Await(Task result)
        {
            await result;
        }
    }
}

//  ##########################################################################
