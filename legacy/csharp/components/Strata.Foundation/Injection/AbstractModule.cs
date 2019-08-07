//  ##########################################################################
//  # File Name: AbstractModule.cs
//  # Copyright: 2017, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Injection
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractModule:
        IModule
    {
        public string         Name { get; protected set; }
        public IModuleAdapter Adapter { get; set; }
        public static Type    DefaultScope { get; set; }

        static 
        AbstractModule()
        {
            DefaultScope = typeof(ThreadScope);
        }


        protected
        AbstractModule(string name)
        {
            Name         = name;
        }

        public abstract void 
        Initialize();

        protected ISourceBindingBuilder<T> 
        Bind<T>()
        {
            return Adapter.Bind<T>();
        }
    }
}

//  ##########################################################################
