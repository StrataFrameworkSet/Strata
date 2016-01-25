//  ##########################################################################
//  # File Name: IServerModule.cs
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
    public
    interface IServerModule
    {
        String Name { get; }

        void 
        Initialize(IServerBootstrapper bootstrapper);
    }
}

//  ##########################################################################
