//  ##########################################################################
//  # File Name: AbstractServerModule.cs
//  # Copyright: 2012, Capital Group Companies, Inc.
//  ##########################################################################

using System;

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
    class AbstractServerModule:
        IServerModule
    {
        public String Name { get; protected set; }
 
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a <c>AbstractServerModule</c> sub-instance.
        /// </summary>
        /// 
        public 
        AbstractServerModule(String name)
        {
            Name = name;
        }

        //////////////////////////////////////////////////////////////////////
        /// <see cref="IServerModule.Initialize(IServerBootstrapper)"/>
        /// <summary>
        /// Concrete modules must override this method to 
        /// implement their initialization process.
        /// </summary>
        /// 
        public abstract void
        Initialize(IServerBootstrapper bootstrapper);
    }
}

//  ##########################################################################
