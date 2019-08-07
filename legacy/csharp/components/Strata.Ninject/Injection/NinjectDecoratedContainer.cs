//  ##########################################################################
//  # File Name: NinjectDecoratedContainer.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject;
using Ninject.Activation;
using Ninject.Activation.Blocks;
using Ninject.Components;
using Ninject.Modules;
using Ninject.Parameters;
using Ninject.Planning.Bindings;
using Ninject.Syntax;
using Strata.Foundation.Injection;
using System;
using System.Collections.Generic;
using System.Reflection;

namespace Strata.Ninject.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class NinjectDecoratedContainer:
        DecoratedContainer,
        IKernel
    {
        public INinjectSettings 
        Settings { get { return itsKernel.Settings; } }

        public IComponentContainer 
        Components { get { return itsKernel.Components; } }

        public bool IsDisposed { get { return itsKernel.IsDisposed; } }

        public event EventHandler Disposed;

        private IKernel itsKernel;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        NinjectDecoratedContainer(
            IDecoratorMap  decorators,
            IList<IModule> modules)
        {
            NinjectContainer target = new NinjectContainer(this,modules);

            itsKernel = target.Kernel;
            Initialize(target,decorators);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        NinjectDecoratedContainer(
            IDecoratorMap    decorators,
            params IModule[] modules)
        {
            NinjectContainer target = new NinjectContainer(this,modules);

            itsKernel = target.Kernel;
            Initialize(target,decorators);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IEnumerable<INinjectModule> 
        GetModules()
        {
            return itsKernel.GetModules();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        HasModule(string name)
        {
            return itsKernel.HasModule(name);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Load(IEnumerable<INinjectModule> m)
        {
            itsKernel.Load(m);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Load(IEnumerable<string> filePatterns)
        {
            itsKernel.Load(filePatterns);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Load(IEnumerable<Assembly> assemblies)
        {
            itsKernel.Load(assemblies);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Unload(string name)
        {
            itsKernel.Unload(name);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IEnumerable<IBinding> 
        GetBindings(Type service)
        {
            return itsKernel.GetBindings(service);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IActivationBlock
        BeginBlock()
        {
            return itsKernel.BeginBlock();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T> 
        Bind<T>()
        {
            return itsKernel.Bind<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2> 
        Bind<T1,T2>()
        {
            return itsKernel.Bind<T1,T2>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2,T3> 
        Bind<T1,T2,T3>()
        {
            return itsKernel.Bind<T1,T2,T3>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2,T3,T4> 
        Bind<T1,T2,T3,T4>()
        {
            return itsKernel.Bind<T1,T2,T3,T4>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<object> 
        Bind(params Type[] services)
        {
            return itsKernel.Bind(services);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Unbind<T>()
        {
            itsKernel.Unbind<T>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Unbind(Type service)
        {
            itsKernel.Unbind(service);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1> 
        Rebind<T1>()
        {
            return itsKernel.Rebind<T1>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2> 
        Rebind<T1,T2>()
        {
            return itsKernel.Rebind<T1,T2>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2,T3> 
        Rebind<T1,T2,T3>()
        {
            return itsKernel.Rebind<T1,T2,T3>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<T1,T2,T3,T4> 
        Rebind<T1,T2,T3,T4>()
        {
            return itsKernel.Rebind<T1,T2,T3,T4>();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IBindingToSyntax<object> 
        Rebind(params Type[] services)
        {
            return itsKernel.Rebind(services);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        AddBinding(IBinding binding)
        {
            itsKernel.AddBinding(binding);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        RemoveBinding(IBinding binding)
        {
            itsKernel.RemoveBinding(binding);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void
        Inject(object instance,params IParameter[] parameters)
        {
            itsKernel.Inject(instance,parameters);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        CanResolve(IRequest request)
        {
            return itsKernel.CanResolve(request);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        CanResolve(IRequest request,bool ignoreImplicitBindings)
        {
            return itsKernel.CanResolve(request,ignoreImplicitBindings);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IEnumerable<object> 
        Resolve(IRequest request)
        {
            IList<object>  decorated = new List<object>();

            foreach (object instance in itsKernel.Resolve(request))
                decorated.Add(Decorate(request.Service,instance));

            return decorated;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual IRequest
        CreateRequest(
            Type                        service,
            Func<IBindingMetadata,bool> constraint,
            IEnumerable<IParameter>     parameters,
            bool                        isOptional,
            bool                        isUnique)
        {
            return
                itsKernel.CreateRequest(
                    service,
                    constraint,
                    parameters,
                    isOptional,
                    isUnique);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual bool
        Release(object instance)
        {
            return itsKernel.Release(instance);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual object
        GetService(Type serviceType)
        {
            return itsKernel.GetService(serviceType);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public virtual void 
        Dispose()
        {
            itsKernel.Dispose();
        }
    }
}

//  ##########################################################################
