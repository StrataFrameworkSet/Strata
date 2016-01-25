//  ##########################################################################
//  # File Name: AbstractServerFactory.cs
//  # Copyright: 2012, Capital Group Companies, Inc.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Configuration;
using Strata.Common.Logging;
using Strata.Common.Task;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.Configuration;

namespace Strata.Server.Bootstrapper
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractServerFactory:
        IServerFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a <c>AbstractServerFactory</c> sub-instance.
        /// </summary>
        /// 
        protected  
        AbstractServerFactory() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public virtual IUnityContainer
        CreateContainer()
        {
            IUnityContainer           output = null;
            UnityConfigurationSection section = null;

            output = new UnityContainer();

            section = 
                (UnityConfigurationSection)
                    ConfigurationManager.GetSection("unity");

            if ( section == null )
                throw new NullReferenceException("Unity section is null.");

            section.Configure(output);
            return output;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public virtual ILogger 
        CreateLogger()
        {
            return new DefaultLogger();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// <summary>
        /// Concrete server factories must implement this method to 
        /// return the list of modules that make up their servers.
        /// </summary>
        /// 
        public abstract IList<IServerModule> 
        CreateModules();

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public abstract IStartupShutdownController 
        CreateStartupShutdownController();
    }
}

//  ##########################################################################
