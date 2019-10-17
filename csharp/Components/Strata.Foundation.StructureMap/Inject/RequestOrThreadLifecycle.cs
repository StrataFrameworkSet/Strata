//  ##########################################################################
//  # File Name: RequestOrThreadLifecycle.cs
//  ##########################################################################

using StructureMap;
using StructureMap.Pipeline;

namespace Strata.Foundation.StructureMap.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class RequestOrThreadLifecycle:
        LifecycleBase
    {
        private readonly ILifecycle implementation;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        RequestOrThreadLifecycle()
        {
            if (RequestLifecyle.HasContext())
                implementation = new RequestLifecyle();
            else
                implementation = new ThreadLocalStorageLifecycle();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override void 
        EjectAll(ILifecycleContext context)
        {
            implementation.EjectAll(context);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override IObjectCache 
        FindCache(ILifecycleContext context)
        {
            return implementation.FindCache(context);
        }
    }
}

//  ##########################################################################
