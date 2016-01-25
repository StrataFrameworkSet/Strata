//  ##########################################################################
//  # File Name: MainClientModule.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Bootstrap;
using Microsoft.Practices.Unity;

namespace Strata.Client.MainClient
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>jfl</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class MainClientModule:
        AbstractModule
    {
        public 
        MainClientModule(): 
            base( "MainClientModule" ) {}

        public 
        MainClientModule(String name): 
            base( name ) {}

        public override void
        Initialize(IUnityContainer container)
        {
            container
                .RegisterType<IMainModel,MainModel>(
                     new ContainerControlledLifetimeManager())          
                .RegisterType<IMainViewModel,MainViewModel>(
                     new ContainerControlledLifetimeManager())
                .RegisterType<IMainController,MainController>(
                     new ContainerControlledLifetimeManager());
        }
    }
}

//  ##########################################################################
