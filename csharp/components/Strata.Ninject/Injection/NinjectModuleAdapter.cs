﻿//  ##########################################################################
//  # File Name: NinjectModuleAdapter.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Modules;
using Strata.Foundation.Injection;
using System;
using System.ComponentModel;

namespace Strata.Ninject.Injection
{
    using INinjectBinding = global::Ninject.Planning.Bindings.IBinding;
    using NinjectBinding = global::Ninject.Planning.Bindings.Binding;

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class NinjectModuleAdapter:
        NinjectModule,
        IModuleAdapter
    {
        public override string Name { get { return itsAdaptee.Name; } }

        private IModule itsAdaptee;

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        SetAdaptee(IModule adaptee)
        {
            
            itsAdaptee = adaptee;
            ((AbstractModule)itsAdaptee).Adapter = this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public ISourceBindingBuilder<T>
        Bind<T>()
        {
            Type type = typeof(T);

            return 
                new NinjectSourceBindingBuilderAdapter<T>( base.Bind<T>() );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public override void 
        Load()
        {
            itsAdaptee.Initialize();
        }
    }
}

//  ##########################################################################
