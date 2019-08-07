//  ##########################################################################
//  # File Name: Bootstrapper.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class Bootstrapper<M,C>:
        IBootstrapper<M,C>
        where M: class
        where C: class
    {
        public C Container { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public
        Bootstrapper()
        {
            Container = null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Run(IApplicationFactory<M,C> factory)
        {
            CreateContainer(factory);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Run(IApplicationFactory<M,C> factory,string[] arguments)
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        CreateContainer(IApplicationFactory<M,C> factory)
        {
            Container = factory.CreateContainer();

            if (Container == null)
                throw new InvalidOperationException("container is null");
        }
    }
}

//  ##########################################################################
