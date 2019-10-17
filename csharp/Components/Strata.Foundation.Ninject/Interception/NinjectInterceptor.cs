//  ##########################################################################
//  # File Name: NinjectInterceptor.cs
//  ##########################################################################

using Strata.Foundation.Core.Interception;

namespace Strata.Foundation.Ninject.Interception
{
    using INinjectInterceptor =
        global::Ninject.Extensions.Interception.IInterceptor;
    using INinjectInvocation =
        global::Ninject.Extensions.Interception.IInvocation;


    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class NinjectInterceptor<I>:
        INinjectInterceptor
        where I: IInterceptor, new()
    {
        public I TargetInterceptor { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        NinjectInterceptor()
        {
            TargetInterceptor = new I(); 
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public void 
        Intercept(INinjectInvocation invocation)
        {
            TargetInterceptor.Intercept(new NinjectInvocation(invocation));
        }
    }
}

//  ##########################################################################
