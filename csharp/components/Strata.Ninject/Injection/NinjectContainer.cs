//  ##########################################################################
//  # File Name: NinjectContainer.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using Ninject.Modules;
using Strata.Foundation.Injection;
using System;
using System.Collections.Generic;
using System.Linq;

namespace Strata.Ninject.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectContainer :
        IContainer
    {
        public IKernel Kernel { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public
        NinjectContainer(IList<IModule> modules)
        {
            IList<INinjectModule> ninjectModules = new List<INinjectModule>();

            ninjectModules.Add(new NinjectSetupModule(this));

            foreach (IModule module in modules)
            {
                NinjectModuleAdapter adapter = new NinjectModuleAdapter();

                adapter.SetAdaptee(module);
                ninjectModules.Add(adapter);
            }

            Kernel = new StandardKernel(ninjectModules.ToArray());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public
        NinjectContainer(IContainer wrapper,IList<IModule> modules)
        {
            IList<INinjectModule> ninjectModules = new List<INinjectModule>();

            ninjectModules.Add(new NinjectSetupModule(wrapper));

            foreach (IModule module in modules)
            {
                NinjectModuleAdapter adapter = new NinjectModuleAdapter();

                adapter.SetAdaptee(module);
                ninjectModules.Add(adapter);
            }

            Kernel = new StandardKernel(ninjectModules.ToArray());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public
        NinjectContainer(params IModule[] modules) :
            this(new List<IModule>(modules))
        { }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public
        NinjectContainer(IContainer wrapper,params IModule[] modules):
            this( wrapper,new List<IModule>( modules ) ) {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T
        GetInstance<T>()
            where T: class
        {
            return Kernel.Get<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        GetInstance<T>(string key)
            where T: class
        {
            return Kernel.Get<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public T 
        GetInstance<T,K>(K key) 
            where T: class
            where K: Attribute
        {
            throw new NotImplementedException();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasBinding<T>()
        {
            return Kernel.CanResolve<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasBinding<T>(string key)
        {
            return Kernel.CanResolve<T>(key);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public bool 
        HasBinding<T,K>(K key) where K : Attribute
        {
            throw new NotImplementedException();
        }
    }
}

//  ##########################################################################
