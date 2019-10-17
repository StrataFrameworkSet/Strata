//  ##########################################################################
//  # File Name: ApplicationBuilderExtension.cs
//  ##########################################################################

using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using Strata.Foundation.StructureMap.Inject;

namespace Strata.Foundation.StructureMap.Bootstrap
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public static
    class ApplicationBuilderExtension
    {
        public static IApplicationBuilder
        UseRequestLifecycle(this IApplicationBuilder app)
        {
            RequestLifecyle
                .SetAccessor(
                    app
                        .ApplicationServices
                        .GetRequiredService<IHttpContextAccessor>());

            return app;
        }

    }
}

//  ##########################################################################
