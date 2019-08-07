//  ##########################################################################
//  # File Name: AbstractClientModule.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Unity;

namespace Strata.Client.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractClientModule<M1,M2,V1,V2,C1,C2>:
        AbstractModule
        where M2:M1
        where V2:V1
        where C2:C1
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a <c>AbstractClientModule</c>.
        /// </summary>
        /// 
        protected 
        AbstractClientModule(String name): 
            base( name ) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        public override void 
        Initialize(IUnityContainer container)
        {
            RegisterModel( container );
            RegisterViewModel( container );
            RegisterController( container );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        protected void
        RegisterModel(IUnityContainer container)
        {
            container
                .RegisterType<M1,M2>(
                    new ContainerControlledLifetimeManager() );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        protected abstract void
        RegisterViewModel(IUnityContainer container);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// 
        /// </summary>
        /// 
        protected void
        RegisterController(IUnityContainer container)
        {
            container
                .RegisterType<C1,C2>(
                        new ContainerControlledLifetimeManager() );            
        }
    }
}

//  ##########################################################################
