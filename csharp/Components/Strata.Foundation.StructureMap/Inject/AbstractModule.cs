//  ##########################################################################
//  # File Name: AbstractModule.cs
//  ##########################################################################

using System;
using StructureMap;
using StructureMap.Pipeline;

namespace Strata.Foundation.StructureMap.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public abstract
    class AbstractModule:
        Registry
    {
        public static ILifecycle DefaultLifecycle { get; set; }

        static
        AbstractModule()
        {
            DefaultLifecycle = new ThreadLocalStorageLifecycle();
        }
        
    }
}

//  ##########################################################################
