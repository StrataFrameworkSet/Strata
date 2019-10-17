//  ##########################################################################
//  # File Name: RequestLifecyle.cs
//  ##########################################################################

using System.Collections.Generic;
using System.Reflection;
using Microsoft.AspNetCore.Http;
using StructureMap;
using StructureMap.Pipeline;

namespace Strata.Foundation.StructureMap.Inject
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class RequestLifecyle:
        LifecycleBase
    {
        public static readonly string ITEM_NAME = 
            string.Format(
                "STRUCTUREMAP-INSTANCES-{0}",
                Assembly.GetExecutingAssembly().GetName().Version);

        private static IHttpContextAccessor accessor;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        RequestLifecyle() {}

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override void 
        EjectAll(ILifecycleContext context)
        {
            FindCache(context).DisposeAndClear();
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override IObjectCache 
        FindCache(ILifecycleContext context)
        {
            IDictionary<object,object> items = GetHttpDictionary();

            if (!items.ContainsKey(ITEM_NAME))
            {
                lock (items)
                {
                    if (!items.ContainsKey(ITEM_NAME))
                    {
                        var cache = new LifecycleObjectCache();
                        items.Add(ITEM_NAME,cache);

                        return cache;
                    }
                }
            }

            return (IObjectCache)items[ITEM_NAME];
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Remove and dispose all objects scoped by HttpContext.
        /// Call this method at the *end* of an http request
        /// to clean up resources.
        /// </summary>
        public void 
        DisposeAndClearAll()
        {
            FindCache(null).DisposeAndClear();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static void
        SetAccessor(IHttpContextAccessor a)
        {
            accessor = a;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public static bool
        HasContext()
        {
            return accessor != null && accessor.HttpContext != null;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected virtual IDictionary<object,object>
        GetHttpDictionary()
        {
            if (!HasContext())
                throw 
                    new StructureMapException(
                        "You cannot use the RequestLifecycle " +
                        "outside of a web request.");

            return accessor.HttpContext.Items;
        }
    }
}

//  ##########################################################################
