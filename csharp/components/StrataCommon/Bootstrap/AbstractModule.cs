//  ##########################################################################
//  # File Name: AbstractModule.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using Microsoft.Practices.Unity;

namespace Strata.Common.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public abstract
    class AbstractModule:
        IModule
    {
        public String Name { get; protected set; }
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a <c>AbstractModule</c> sub-instance.
        /// </summary>
        /// 
        protected 
        AbstractModule(String name)
        {
            Name = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <inheritDoc/>
        /// Concrete modules must override this method to 
        /// implement their initialization process.
        /// </summary>
        /// 
        public abstract void
        Initialize(IUnityContainer container);
    }
}

//  ##########################################################################
