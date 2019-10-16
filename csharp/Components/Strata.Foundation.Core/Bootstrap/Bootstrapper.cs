//  ##########################################################################
//  # File Name: Bootstrapper.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Strata.Foundation.Core.Inject;

namespace Strata.Foundation.Core.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class Bootstrapper:
        IBootstrapper
    {
        public IContainer Container { get; protected set; }

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
        Run(IApplicationFactory factory)
        {
            CreateContainer(factory);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Run(IApplicationFactory factory,string[] arguments)
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected void
        CreateContainer(IApplicationFactory factory)
        {
            Container = factory.CreateContainer();

            if (Container == null)
                throw new InvalidOperationException("container is null");
        }
    }
}

//  ##########################################################################
