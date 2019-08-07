//  ##########################################################################
//  # File Name: AbstractBootstrapFactory.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.Configuration;
using Strata.Common.Logging;
using Microsoft.Practices.Unity;
using Microsoft.Practices.Unity.Configuration;

namespace Strata.Common.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractBootstrapFactory:
        IBootstrapFactory
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a <c>AbstractBootstrapFactory</c> sub-instance.
        /// </summary>
        /// 
        protected  
        AbstractBootstrapFactory() {}

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
        public abstract IList<IModule> 
        CreateModules();

        //////////////////////////////////////////////////////////////////////
        /// <inheritdoc/>
        /// 
        public abstract IStartupShutdownController 
        CreateStartupShutdownController();
    }
}

//  ##########################################################################
