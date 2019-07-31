//  ##########################################################################
//  # File Name: AbstractApplicationFactory{L,S,C}.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Utility;
using System.Collections.Generic;

namespace Strata.Foundation.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractApplicationFactory<M,C>:
        IApplicationFactory<M,C>
        where M: class
        where C: class
    {

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void
        BindLogger(M module);

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract void
        BindStartStopController(M module);

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public virtual C 
        CreateContainer()
        {
            IList<M> modules = new List<M>();

            modules.Add(CreateBootstrapModule());
            modules.AddAll(CreateModules());

            return CreateContainer(modules);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected abstract M
        CreateBootstrapModule();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected abstract IList<M>
        CreateModules();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        protected abstract C
        CreateContainer(IList<M> modules);
    }
}

//  ##########################################################################
