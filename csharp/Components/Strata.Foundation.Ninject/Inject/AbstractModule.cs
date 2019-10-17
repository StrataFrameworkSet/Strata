//  ##########################################################################
//  # File Name: AbstractModule.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Ninject.Modules;

namespace Strata.Foundation.Ninject.Inject
{

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public abstract
    class AbstractModule:
        NinjectModule
    {
        public static Scope DefaultScope { get; set; }

        static
        AbstractModule()
        {
            DefaultScope = Scope.THREAD_SCOPE;
        }
    }
}

//  ##########################################################################
